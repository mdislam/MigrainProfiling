package org.mis.profiling.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mis.datewizard.Utils;
import org.mis.profiling.R;
import org.mis.profiling.models.MigrainEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mis on 9/28/2016.
 */

public class MigrainEntryAdapter extends RecyclerView.Adapter<MigrainEntryAdapter.MainViewHolder> {
    LayoutInflater inflater;
    List<MigrainEntry> modelList;

    public MigrainEntryAdapter(Context context, List<MigrainEntry> list) {
        inflater = LayoutInflater.from(context);
        modelList = new ArrayList<>(list);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.migrain_entry_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.bindData(modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvRating, tvFromTime, tvToTime;

        public MainViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvRating = (TextView) itemView.findViewById(R.id.tv_rating);
            tvFromTime = (TextView) itemView.findViewById(R.id.tv_from_time);
            tvToTime = (TextView) itemView.findViewById(R.id.tv_to_time);
        }

        public void bindData(MigrainEntry rowModel) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(rowModel.getStarted());

            int date = cal.get(Calendar.DATE);
            String month = Utils.getMonthName(cal);
            int year = cal.get(Calendar.YEAR);
            String weekDay = Utils.getWeekDay(rowModel.getStarted());

            int hour = cal.get(Calendar.HOUR);
            int minute = cal.get(Calendar.MINUTE);

            tvDate.setText(month + " " + date + ", " + year); //weekDay + ", " +
            tvRating.setText("" + rowModel.getLevel());

            tvFromTime.setText(hour + ":" + minute);

            cal.setTime(rowModel.getEnded());
            hour = cal.get(Calendar.HOUR);
            minute = cal.get(Calendar.MINUTE);
            tvToTime.setText(hour + ":" + minute);
        }
    }
}
