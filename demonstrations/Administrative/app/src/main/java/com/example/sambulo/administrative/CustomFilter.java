package com.example.sambulo.administrative;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mokgako on 2017/10/13.
 */

class CustomFilter extends Filter {
    List<Learner> filters;
    CustomAdminShopAdapter adapter;

    public CustomFilter(List<Learner> learners, CustomAdminShopAdapter adapter) {
        this.filters = learners;
        this.adapter = adapter;
    }


    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults filterResults = new FilterResults();

        if (constraint != null && constraint.length() > 0) {

            constraint = constraint.toString().toUpperCase();
            List<Learner> filteredLearners = new ArrayList<Learner>();


            for (int i = 0; i < filters.size(); i++) {
                if (filters.get(i).getLearnerName().toUpperCase().contains(constraint)) {

                    Learner l = new Learner(filters.get(i).getLearnerName(), filters.get(i).getLearnerSurname(), filters.get(i).getClassName(), filters.get(i).getTuckBalance());
                    filteredLearners.add(l);

                }

            }
            filterResults.count = filteredLearners.size();
            filterResults.values = filteredLearners;

        } else {
            filterResults.count = filters.size();
            filterResults.values = filters;

        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.learner = (List<Learner>) results.values;
        adapter.notifyDataSetChanged();


    }
}

