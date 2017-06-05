package lt.adient.mobility.eLPA.adapter;

import android.content.Context;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.Workstation;

public class WorkstationAdapter extends BaseAdapter<Workstation> {
    public WorkstationAdapter(Context context, int resource, List<Workstation> items) {
        super(context, resource, items);
    }

    public int findWorkstationByID(String idWorkstation) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i) != null && getItem(i).getMachineId() != null && getItem(i).getMachineId().equals(idWorkstation)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String getText(int position) {
        return getItem(position).getMachineName();
    }

}
