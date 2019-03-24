package com.mujeeb.nycschools.view.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mujeeb.nycschools.R;
import com.mujeeb.nycschools.model.Academic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AcademicListRecyclerViewAdapter extends RecyclerView.Adapter<AcademicListRecyclerViewAdapter.AcademicResultViewHolder> {
    private static AcademicSelectedInterface listener;
    private final List<Academic> academicList;

    public AcademicListRecyclerViewAdapter(@NonNull AcademicSelectedInterface listener) {
        academicList = new ArrayList<>();
        AcademicListRecyclerViewAdapter.listener = listener;
    }

    public void setData(@NonNull List<Academic> data) {
        Objects.requireNonNull(data);
        academicList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        academicList.clear();
    }

    @NonNull
    @Override
    public AcademicResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.academic_item, viewGroup, false);
        return new AcademicResultViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull AcademicResultViewHolder academicResultViewHolder, int position) {
        academicResultViewHolder.bind(academicList.get(position));
    }

    @Override
    public int getItemCount() {
        return academicList.size();
    }

    static class AcademicResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_academic_name)
        TextView tvSchoolName;
        @BindView(R.id.tv_school_city)
        TextView tvSchoolCity;
        @BindView(R.id.tv_phone_number)
        TextView tvPhoneNumber;
        @BindView(R.id.card_view)
        CardView cardView;

        public AcademicResultViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Academic academic) {
            tvSchoolName.setText(academic.getSchoolName());
            tvSchoolCity.setText(academic.getCity());
            tvPhoneNumber.setText(academic.getPhoneNumber());
        }

        @OnClick(R.id.card_view)
        void handleSearchResultSelected() {
            listener.onResultSelected(this.getLayoutPosition());
        }
    }
}
