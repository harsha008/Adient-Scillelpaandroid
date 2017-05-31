package lt.sciil.eLPA.adapter;

import android.content.Context;

import java.util.List;
import java.util.Objects;

import lt.sciil.eLPA.database.model.Language;

public class LanguageAdapter extends BaseAdapter<Language> {

    public LanguageAdapter(Context context, int textViewResourceId, List<Language> items) {
        super(context, textViewResourceId, items);
    }

    public int getIndexByLanguageId(Long languageId) {
        for (int i = 0; i < getCount(); i++) {
            if (Objects.equals(getItem(i).getId(), languageId)) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public String getText(int position) {
        return getItem(position).getLanguageName();
    }
}
