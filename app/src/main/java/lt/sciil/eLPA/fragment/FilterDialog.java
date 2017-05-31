package lt.sciil.eLPA.fragment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lt.sciil.eLPA.App;
import lt.sciil.eLPA.R;
import lt.sciil.eLPA.adapter.UserAdapter;
import lt.sciil.eLPA.adapter.WorkstationAdapter;
import lt.sciil.eLPA.database.UserRepository;
import lt.sciil.eLPA.database.WorkstationRepository;
import lt.sciil.eLPA.database.model.FilterParameters;
import lt.sciil.eLPA.database.model.Planned;
import lt.sciil.eLPA.database.model.User;
import lt.sciil.eLPA.database.model.ViewToTranslate;
import lt.sciil.eLPA.database.model.Workstation;
import lt.sciil.eLPA.events.FilterEvent;
import lt.sciil.eLPA.utils.JsonHelper;
import lt.sciil.eLPA.utils.PrefManager;
import lt.sciil.eLPA.utils.Translator;
import lt.sciil.eLPA.utils.Utility;

import static lt.sciil.eLPA.utils.Utility.showTranslatedToast;


public class FilterDialog extends DialogFragment {
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private DateFormat dateFormatter;
    private UserAdapter userSpinnerAdapter;
    private WorkstationAdapter workstationSpinnerAdapter;
    private Calendar fromDate;
    private Calendar toDate;
    private Calendar newCalendarTo = Calendar.getInstance();
    private Calendar newCalendarFrom = Calendar.getInstance();
    private Workstation selectedWorkstation;
    private Dialog dialog;

    @Inject
    Translator translator;
    @Inject
    UserRepository userRepository;
    @Inject
    WorkstationRepository workstationRepository;
    @Inject
    PrefManager prefManager;
    @BindView(R.id.spinner_user)
    Spinner spinnerUser;
    @BindView(R.id.spinner_work_station)
    AutoCompleteTextView workStationAutocomplete;
    @BindView(R.id.clear_user_image_view)
    ImageView clearUser;
    @BindView(R.id.clear_workstation_image_view)
    ImageView clearWorkstation;
    @BindView(R.id.clear_to_date_image_view)
    ImageView clearToDate;
    @BindView(R.id.clear_from_date_image_view)
    ImageView clearFromDate;
    @BindView(R.id.filterButton)
    Button filterButton;
    @BindView(R.id.fromDateEditText)
    EditText fromDateEditText;
    @BindView(R.id.toDateEditText)
    EditText toDateEditText;
    @BindView(R.id.fromLabel)
    TextView fromLabel;
    @BindView(R.id.toLabel)
    TextView toLabel;
    @BindView(R.id.userLabel)
    TextView userLabel;
    @BindView(R.id.workstationLabel)
    TextView workstationLabel;

    public FilterDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        ButterKnife.bind(this, rootView);
        App.getApplicationComponent().inject(this);
        fromDateEditText.setInputType(InputType.TYPE_NULL);
        toDateEditText.setInputType(InputType.TYPE_NULL);

        dialog = getDialog();
        dialog.setTitle(getString(R.string.filter));

        dateFormatter = android.text.format.DateFormat.getDateFormat(getActivity());
        setDateTimeField();

        setupUserSpinner();
        setupWorkstationSpinner();

        clearUser.setOnClickListener(v -> spinnerUser.setSelection(0));

        clearWorkstation.setOnClickListener(v -> {
            workStationAutocomplete.setText("");
            selectedWorkstation = null;
        });

        clearFromDate.setOnClickListener(v -> {
            fromDate = null;
            fromDateEditText.setText("");
            newCalendarFrom = Calendar.getInstance();
        });

        clearToDate.setOnClickListener(v -> {
            toDate = null;
            toDateEditText.setText("");
            newCalendarTo = Calendar.getInstance();
        });

        workStationAutocomplete.setThreshold(1);
        workStationAutocomplete.setOnClickListener(v -> {
            workStationAutocomplete.showDropDown();
        });

        workStationAutocomplete.setOnItemClickListener((parent, view, position, id) -> {
            selectedWorkstation = workstationSpinnerAdapter.getItem(position);
            Utility.hideKeyboardFrom(getActivity(), workStationAutocomplete);
        });

        filterButton.setOnClickListener(v -> {
            FilterParameters filterParameters = new FilterParameters();
            if (((User) spinnerUser.getSelectedItem()).getUserId() != null) {
                filterParameters.setUserId(((User) spinnerUser.getSelectedItem()).getUserId());
                if (selectedWorkstation != null && !workStationAutocomplete.getText().toString().isEmpty())
                    filterParameters.setMachineId(selectedWorkstation.getMachineId());
                else
                    filterParameters.setMachineId("");

                Planned planned = new Planned();
                Long fromDateMillis = 0L;
                Long toDateMillis = 0L;
                if (fromDate != null) {
                    fromDateMillis = fromDate.getTimeInMillis();
                    planned.setDateFrom(JsonHelper.formatPYPFormat(fromDateMillis));
                }
                if (toDate != null) {
                    toDateMillis = toDate.getTimeInMillis();
                    planned.setDateTo(JsonHelper.formatPYPFormat(toDateMillis));
                }
                filterParameters.setPlanned(planned);
                saveFilterToPreferences(filterParameters, fromDateMillis, toDateMillis);
                EventBus.getDefault().postSticky(new FilterEvent(filterParameters));
            } else {
                showTranslatedToast(getActivity(), R.string.filter_select_user, Toast.LENGTH_LONG, translator);
            }
        });

        restoreValues();
        translate();
        return rootView;
    }

    private void saveFilterToPreferences(FilterParameters filterParameters, Long fromDate, Long toDate) {
        prefManager.setFilterPreference(filterParameters, fromDate, toDate);
    }

    private void restoreValues() {
        String userId = prefManager.getFilterUserId();
        int userPos = userSpinnerAdapter.findUserByID(userId);

        if (userPos >= 0) {
            spinnerUser.setSelection(userPos);
        }

        String idWorkstation = prefManager.getFilterMachineId();
        if (!idWorkstation.equals("")) {
            int workstationPos = workstationSpinnerAdapter.findWorkstationByID(idWorkstation);
            if (workstationPos >= 0) {
                workStationAutocomplete.setText(workstationSpinnerAdapter.getItem(workstationPos).getMachineName());
                workStationAutocomplete.getOnItemClickListener().onItemClick(null, null, workstationPos, 0);
                workStationAutocomplete.dismissDropDown();
            }
        }

        Long fromDateMillis = prefManager.getFilterFromDate();
        if (fromDateMillis != 0) {
            fromDate = Calendar.getInstance();
            fromDate.setTimeInMillis(fromDateMillis);
            fromDateEditText.setText(dateFormatter.format(fromDate.getTime()));
        }

        Long toDateMillis = prefManager.getFilterToDate();
        if (toDateMillis != 0) {
            toDate = Calendar.getInstance();
            toDate.setTimeInMillis(toDateMillis);
            toDateEditText.setText(dateFormatter.format(toDate.getTime()));
        }
    }

    private void setupUserSpinner() {
        userSpinnerAdapter = new UserAdapter(getActivity(), R.layout.spinner_dropdown_item, getUsersList());
        spinnerUser.setAdapter(userSpinnerAdapter);
    }

    private void setupWorkstationSpinner() {
        workstationSpinnerAdapter = new WorkstationAdapter(getActivity(), R.layout.spinner_dropdown_item, getWorkstationsList());
        workStationAutocomplete.setAdapter(workstationSpinnerAdapter);
    }

    private List<User> getUsersList() {
        return userRepository.getAllUsers();
    }

    private List<Workstation> getWorkstationsList() {
        return workstationRepository.getAllWorkstations();

    }


    private void setDateTimeField() {
        long dateFrom = prefManager.getFilterFromDate();
        if (dateFrom != 0) {
            newCalendarFrom.setTimeInMillis(dateFrom);
        }

        fromDateEditText.setOnClickListener(v -> fromDatePickerDialog.show());

        fromDatePickerDialog = new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> {

            fromDate = Calendar.getInstance();
            fromDate.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
            fromDate.set(Calendar.MILLISECOND, 0);
            fromDateEditText.setText(dateFormatter.format(fromDate.getTime()));
        }, newCalendarFrom.get(Calendar.YEAR), newCalendarFrom.get(Calendar.MONTH), newCalendarFrom.get(Calendar.DAY_OF_MONTH));

        Long dateTo = prefManager.getFilterToDate();
        if (dateTo != 0) {
            newCalendarTo.setTimeInMillis(dateTo);
        }


        toDateEditText.setOnClickListener(v -> toDatePickerDialog.show());

        toDatePickerDialog = new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            toDate = Calendar.getInstance();
            toDate.set(year, monthOfYear, dayOfMonth, 23, 59, 59);
            toDateEditText.setText(dateFormatter.format(toDate.getTime()));
        }, newCalendarTo.get(Calendar.YEAR), newCalendarTo.get(Calendar.MONTH), newCalendarTo.get(Calendar.DAY_OF_MONTH));
    }


    private void translate() {
        translator.translate(new ViewToTranslate<>(dialog, "filter"));
        translator.translate(userLabel, workstationLabel, fromLabel, toLabel, filterButton, fromDateEditText, toDateEditText);
    }
}
