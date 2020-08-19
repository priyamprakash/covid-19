package app.covid_19;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
//list view ko handle krne k lye
public class MyCustomAdapter extends ArrayAdapter<CountryModel> {
    //things to be provided
    private Context context;
    private List<CountryModel> countryModelsList; //country model type ka list
    private List<CountryModel> countryModelsListFiltered;

    public MyCustomAdapter( Context context, List<CountryModel> countryModelsList) {
        super(context, R.layout.list_custom_item,countryModelsList);
// context, xml jisse connect krna h

        this.context = context; //context ko iss parameter me set
        this.countryModelsList = countryModelsList;         //original list
        this.countryModelsListFiltered = countryModelsList; // filtered list

    }
//jo data aaega usko getview me set krenge
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);

        ImageView imageView = view.findViewById(R.id.imageFlag);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);

        tvCountryName.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelsListFiltered.get(position).getFlag()).into(imageView);
//with k andr content pass krenge
//filter wala list use krenge

        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();  // filtered list ka size count krke return
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelsListFiltered.get(position);//position k help se bs filtered list aaenge
    }

    @Override
    public long getItemId(int position) {
        return position; // position aaegi
    }

    @Override
    public Filter getFilter()

    {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;

                }else{
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel itemsModel:countryModelsList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelsListFiltered = (List<CountryModel>) results.values;
                AffectedCountries.countryModelsList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
