package oneshoppoint.com.ishop;

/**
 * Created by stephineosoro on 24/05/16.
 */

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.NetworkImageView;

        import java.util.List;

        import oneshoppoint.com.ishop.fragment.CategoryFragment;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Items> products;
    private Context context;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public NetworkImageView thumbnail;
        public TextView CategoryName;
//        public TextView price;
//        public TextView genre;
        public TextView TheID;
//        public TextView Starttime;


        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (NetworkImageView) itemView.findViewById(R.id.thumbnail);
            CategoryName = (TextView) itemView.findViewById(R.id.textViewName);
            TheID = (TextView) itemView.findViewById(R.id.theID);

        }


    }

    public void add(int position, Items items) {
        products.add(position, items);
        notifyItemInserted(position);
    }

    public void remove(Items items) {
        int position = products.indexOf(items);
        products.remove(position);
        notifyItemRemoved(position);
    }

    public CategoryAdapter(Context context, List<Items> products) {
        this.products = products;
        this.context = context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        create a new view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_category, viewGroup, false);
//        set the view's size, margins, paddings and layout parameters
//        view.setOnClickListener(ProductsRefined.myOnClickListener);
//        view.
        view.setOnClickListener(Home.myOnClickListener);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder viewHolder, int position) {
        imageLoader = AppController.getInstance().getImageLoader();

//        Get elements from products at this position and
//        replace contents of view with that element

        Items items = products.get(position);

        viewHolder.thumbnail.setImageUrl(items.getThumbnailUrl(), imageLoader);
        viewHolder.CategoryName.setText(items.getTitle());
//        viewHolder.price.setText(String.valueOf(items.getPrice()));

//        String genreStr = "";
//        for (String s : items.getGenre())
//            genreStr += s + ", ";
//
//        genreStr = genreStr.length() > 0 ? genreStr.substring(0, genreStr.length() - 2) : genreStr;
//        viewHolder.Type.setText(genreStr);

       viewHolder.TheID.setText(String.valueOf(items.getTheID()));
//        viewHolder.Starttime.setText(String.valueOf(items.getDescription()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}


