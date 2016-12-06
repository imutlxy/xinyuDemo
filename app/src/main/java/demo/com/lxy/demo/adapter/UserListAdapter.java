package demo.com.lxy.demo.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import demo.com.lxy.demo.R;
import demo.com.lxy.demo.moduel.User;
import demo.com.lxy.demo.util.BaseActivity;

/**
 * Created by lxy on 16-12-6.
 */
public class UserListAdapter extends BaseAdapter {

    private ArrayList<User> users;
    private BaseActivity mActivity;

    public UserListAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        users = new ArrayList<>();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = mActivity.getLayoutInflater().inflate(R.layout.user_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) view.findViewById(R.id.user_name);
            viewHolder.language = (TextView) view.findViewById(R.id.language);

//            viewHolder.userAvatar = (SimpleDraweeView) view.findViewById(R.id.user_avatar);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        User user = users.get(i);
        viewHolder.userName.setText(user.getUserName());

        if (TextUtils.isEmpty(user.getLanguage())) {
            viewHolder.language.setText("");
        } else {
            viewHolder.language.setText(user.getLanguage());
        }
//        setUserAvatar(viewHolder.userAvatar,user.getUserAvatarUrl());

        return view;
    }

    static class ViewHolder {
        public TextView userName;
        public TextView language;
//        public SimpleDraweeView userAvatar;
    }

//    private void setUserAvatar(SimpleDraweeView userAvatar,String avatarUrl){
//        Uri uri = Uri.parse(avatarUrl);
//        userAvatar.setImageURI(uri);
//
//        //获取GenericDraweeHierarchy对象
//        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
//                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
//                .setRoundingParams(RoundingParams.asCircle())
//                        //设置淡入淡出动画持续时间(单位：毫秒ms)
////                    .setFadeDuration(5000)
//                        //构建
//                .build();
//        userAvatar.setHierarchy(hierarchy);
//
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setTapToRetryEnabled(true)
//                .build();
//        userAvatar.setController(draweeController);
//    }
}
