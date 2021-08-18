package com.meetingselect.weatherlyv2.main.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.meetingselect.weatherlyv2.R;
import com.meetingselect.weatherlyv2.data.Model.SearchLocation.CityResultsItem;

import java.util.List;

public class SearchCitiesAdapter extends RecyclerView.Adapter<SearchCitiesAdapter.ViewHolder> {

    private static final String TAG = "SearchCities";

    private Context context;
    private onCityNameClicked onCityNameClicked;

    private MutableLiveData<List<CityResultsItem>> mCityResultsMutableLiveData = new MutableLiveData<>();

    @NonNull
    @Override
    public SearchCitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchcities_cityresults_recyclerview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public SearchCitiesAdapter(Context context, onCityNameClicked onCityNameClicked) {
        this.context = context;
        this.onCityNameClicked = onCityNameClicked;
    }

    public void updateData(MutableLiveData<List<CityResultsItem>> mCityResultsMutableLiveData) {
        this.mCityResultsMutableLiveData = mCityResultsMutableLiveData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCitiesAdapter.ViewHolder holder, int position) {
        holder.CityName.setText(mCityResultsMutableLiveData.getValue().get(position).getName());

        holder.CityName.setOnClickListener(v -> {
            String cityname = mCityResultsMutableLiveData.getValue().get(position).getName();
            onCityNameClicked.onCityNameClicked(cityname);

        });
    }

    @Override
    public int getItemCount() {
        List<CityResultsItem> list = mCityResultsMutableLiveData.getValue();
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView CityName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CityName = itemView.findViewById(R.id.searchCitiesRV_cityname_textview);
        }
    }

    public interface onCityNameClicked {
        void onCityNameClicked(String cityname);
    }
}
