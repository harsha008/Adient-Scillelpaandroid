package lt.adient.mobility.eLPA.adapter;

import android.content.Context;

import java.util.List;

import lt.adient.mobility.eLPA.database.model.User;

public class UserAdapter extends BaseAdapter<User> {
    public UserAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);
    }


    public int findUserByID(String idWorkstation) {
        for (int i = 0; i < getCount(); i++) {
            if (getItem(i) != null && getItem(i).getUserId() != null && getItem(i).getUserId().equals(idWorkstation)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String getText(int position) {
        return getItem(position).getUserName();
    }
}
