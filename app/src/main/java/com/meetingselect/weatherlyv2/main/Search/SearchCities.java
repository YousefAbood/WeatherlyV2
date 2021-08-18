package com.meetingselect.weatherlyv2.main.Search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.meetingselect.weatherlyv2.R;
import com.meetingselect.weatherlyv2.data.Model.SearchLocation.CityResultsItem;
import com.meetingselect.weatherlyv2.mainviewmodel.MainViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SearchCities extends Fragment implements SearchCitiesAdapter.onCityNameClicked{

    private static final String TAG = "SearchCities";

    private CompositeDisposable mDisposable;
    private MutableLiveData<List<CityResultsItem>> mCityNamesMutableLiveData = new MutableLiveData<>();
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_cities, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mDisposable = new CompositeDisposable();
        SearchView mSearchView = view.findViewById(R.id.searchcities_searchcitiesbar_searchview);
        mDisposable.add(
                RxSearchView.queryTextChanges(mSearchView)
                        .filter(text -> text.length() >= 1)
                        .debounce(350, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::getCityResults)
        );

        int searchPlateId = mSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);

        View searchPlate = mSearchView.findViewById(searchPlateId);

        searchPlate.setBackgroundResource(R.drawable.searchcities_searchplatebg);


    }

    private void getCityResults(CharSequence location) {

        RecyclerView recyclerViewCityResults = requireView().findViewById(R.id.searchcities_cityresults_recyclerview);

        Log.d(TAG, "getCityResults: " + location);

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mainViewModel.getCityNames(String.valueOf(location));

        mDisposable = new CompositeDisposable();

        Disposable cityNames = mainViewModel.getmCityName().doOnComplete(() -> Log.d(TAG, "getCityResults: Complete")).subscribe(v -> {
            if(!(v.size() == 0)) {
                recyclerViewCityResults.setVisibility(View.VISIBLE);
                mCityNamesMutableLiveData.setValue(v);
                initRecyclerViewCityResults();
                Log.d(TAG, "getCityResults: " + v.get(0).getCountry());

            } else {
                recyclerViewCityResults.setVisibility(View.INVISIBLE);

            }

        });

        mDisposable.add(cityNames);

    }

    private void initRecyclerViewCityResults() {
        RecyclerView recyclerViewCityResults = requireView().findViewById(R.id.searchcities_cityresults_recyclerview);
        SearchCitiesAdapter adapter = new SearchCitiesAdapter(requireActivity(), this);
        recyclerViewCityResults.setAdapter(adapter);
        recyclerViewCityResults.setLayoutManager(new LinearLayoutManager(requireActivity()));

        adapter.updateData(mCityNamesMutableLiveData);
    }

    private void onErrorSearchCities() {
        Log.d(TAG, "onErrorSearchCities: error ya 7mar");

    }

    @Override
    public void onCityNameClicked(String cityname) {
        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        hideSoftKeyboard(requireActivity());
        alertDialog = new AlertDialog.Builder(requireActivity())
                .setTitle(cityname)
                .setMessage("Do you want to add " + cityname + " to the list of the cities?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainViewModel.addCityName(cityname);
                        Navigation.findNavController(requireView()).navigate(R.id.action_searchCities_to_citiesFragment);
                        alertDialog = null;
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog = null;
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .create();

        alertDialog.show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    @Override
    public void onPause() {
        super.onPause();
        mDisposable.dispose();
    }
}