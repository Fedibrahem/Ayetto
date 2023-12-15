package com.example.ayette;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FormDetailsAdapter extends ArrayAdapter<FormDetails> {

    private List<FormDetails> formDetailsList;
    private List<FormDetails> filteredList;
    private ItemFilter filter = new ItemFilter();
    private Context context;

    public FormDetailsAdapter(Context context, List<FormDetails> formDetailsList) {
        super(context, 0, formDetailsList);
        this.context = context;
        this.formDetailsList = formDetailsList;
        this.filteredList = formDetailsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final FormDetails formDetails = filteredList.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewAge = convertView.findViewById(R.id.textViewAge);
        TextView textViewInitialDate = convertView.findViewById(R.id.textViewInitialDate);
        TextView textViewFinalDate = convertView.findViewById(R.id.textViewFinalDate);
        TextView textViewBudget = convertView.findViewById(R.id.textViewBudget);

        if (formDetails != null) {
            textViewName.setText("Name: " + formDetails.getName());
            textViewAge.setText("Age: " + formDetails.getAge());
            textViewInitialDate.setText("Initial Date: " + formDetails.getInitialDate());
            textViewFinalDate.setText("Final Date: " + formDetails.getFinalDate());
            textViewBudget.setText("Budget: " + formDetails.getBudget());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("formDetails", formDetails);
                context.startActivity(intent);
            }
        });

        convertView.setOnLongClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete Item");
            builder.setMessage("Are you sure you want to delete this item?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                // Perform delete operation using position
                remove(formDetails); // Remove item using position
                notifyDataSetChanged(); // Notify adapter about the change
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
            return true;
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<FormDetails> filtered = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filtered.addAll(formDetailsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FormDetails item : formDetailsList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filtered.add(item);
                    }
                }
            }

            results.values = filtered;
            results.count = filtered.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<FormDetails>) results.values;
            notifyDataSetChanged();
        }
    }
}
