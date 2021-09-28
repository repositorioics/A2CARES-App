package ni.org.ics.a2cares.app.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.supervisor.RecepcionMuestra;
import ni.org.ics.a2cares.app.dto.MuestraDTO;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 9/7/2021.
 */
public class RecepcionListAdapter extends RecyclerView.Adapter<RecepcionListAdapter.ViewHolder> {

    private List<RecepcionMuestra> listdata;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public RecepcionListAdapter(List<RecepcionMuestra> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.complex_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem) {
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RecepcionMuestra recepcionMuestra = listdata.get(position);
        holder.textViewIdent.setText(recepcionMuestra.getTipoTubo());
        holder.textViewIdent.setTextSize(20);
        if (recepcionMuestra.getTipoTubo().equalsIgnoreCase(Constants.TIPO_TUBO_BHC)) {
            holder.textViewIdent.setTextColor(Color.MAGENTA);
        } else {
            holder.textViewIdent.setTextColor(Color.RED);
        }
        holder.textViewDer.setText(mDateFormat.format(listdata.get(position).getFechaRecepcion()));
        holder.textViewName.setText(holder.context.getString(R.string.code) + ": " + recepcionMuestra.getCodigoMx() + " - " + holder.context.getString(R.string.volumen) + ": " + recepcionMuestra.getVolumen() + holder.context.getString(R.string.ml));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void filterList(ArrayList<RecepcionMuestra> filteredList) {
        listdata = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        public ImageView imageView;
        public TextView textViewIdent;
        public TextView textViewDer;
        public TextView textViewName;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.imageView.setVisibility(View.GONE);
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
