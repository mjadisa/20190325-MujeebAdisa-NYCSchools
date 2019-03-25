package com.mujeeb.nycschools.mvp.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mujeeb.nycschools.R;
import com.mujeeb.nycschools.model.School;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SchoolListRecyclerViewAdapter extends RecyclerView.Adapter<SchoolListRecyclerViewAdapter.SchoolResultViewHolder> {
    private static SchoolSelectedInterface listener;
    private final List<School> schoolList;

    public SchoolListRecyclerViewAdapter(@NonNull SchoolSelectedInterface listener) {
        schoolList = new ArrayList<>();
        SchoolListRecyclerViewAdapter.listener = listener;
    }

    public void setData(@NonNull List<School> data) {
        Objects.requireNonNull(data);
        schoolList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        schoolList.clear();
    }

    @NonNull
    @Override
    public SchoolResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.school_item, viewGroup, false);
        return new SchoolResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolResultViewHolder schoolResultViewHolder, int position) {
        schoolResultViewHolder.bind(schoolList.get(position));
    }

    @Override
    public int getItemCount() {
        return schoolList.size();
    }

    static class SchoolResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_school_name)
        TextView tvSchoolName;
        @BindView(R.id.tv_school_city)
        TextView tvSchoolCity;
        @BindView(R.id.tv_phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.card_view)
        CardView cardView;

        public SchoolResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(School school) {
            tvSchoolName.setText(school.getSchoolName());
            tvSchoolCity.setText(school.getCity());
            tvPhoneNumber.setText(school.getPhoneNumber());
        }

        @OnClick(R.id.card_view)
        void handleSearchResultSelected() {
            listener.onResultSelected(this.getLayoutPosition());
        }
    }
}
