package com.wofi.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wofi.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by zxx on 2017/8/4.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.BaseViewHolder> {
    private ArrayList<ItemModel> dataList = new ArrayList<>();
    private int lastPressIndex = -1;
    private Context mContext;

    public void replaceAll(ArrayList<ItemModel> list, Context context) {
        mContext = context;
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public DemoAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {

            case ItemModel.ONE:
                return new CornorViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.corner_one, parent, false));
            case ItemModel.TWO:
                return new OneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recharge_item, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(DemoAdapter.BaseViewHolder holder, int position) {

        holder.setData(dataList.get(position).data);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).type;
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        void setData(Object data) {
        }
    }

    private class OneViewHolder extends BaseViewHolder {
        private TextView tv;

        public OneViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "OneViewHolder: ");
                    notyfy = 0;
                    int position = getAdapterPosition();
                    ItemModel model = dataList.get(position);
                    Log.e("TAG", "OneViewHolder: " + model.toString());
                    EventBus.getDefault().post(model);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();


                }

            });
        }

        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                tv.setText(text);
                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));

                } else {
                    tv.setSelected(false);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blue));
                }

            }


        }
    }

    int notyfy = 0;



    private class CornorViewHoler extends BaseViewHolder {
        private TextView tv;

        public CornorViewHoler(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);
            int position = getAdapterPosition();
            ItemModel model = dataList.get(position);
            Log.e("TAG", "OneViewHolder: "+model.toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("TAG", "OneViewHolder: ");
                    notyfy = 0;
                    int position = getAdapterPosition();
                    ItemModel model = dataList.get(position);
                    EventBus.getDefault().post(model);
                    if (lastPressIndex == position) {
                        lastPressIndex = -1;
                    } else {
                        lastPressIndex = position;
                    }
                    notifyDataSetChanged();


                }

            });
        }

        @Override
        void setData(Object data) {
            if (data != null) {
                String text = (String) data;
                tv.setText(text);
                if (getAdapterPosition() == lastPressIndex) {
                    tv.setSelected(true);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));

                } else {
                    tv.setSelected(false);
                    tv.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.blue));
                }

            }
        }
    }
}
