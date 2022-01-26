package ni.org.ics.a2cares.app.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.dto.MuestraDTO;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 9/7/2021.
 */
public class OrdenesLabListAdapter extends RecyclerView.Adapter<OrdenesLabListAdapter.ViewHolder> {

    private List<OrdenLaboratorio> listdata;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public OrdenesLabListAdapter(List<OrdenLaboratorio> listdata) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OrdenLaboratorio muestraDTO = listdata.get(position);
        holder.textViewIdent.setText(muestraDTO.getParticipante().getNombreCompleto());
        //holder.textViewIdent.setTextSize(18);
        /*holder.textViewIdent.setTextColor(muestraDTO.getColor());
        holder.textViewDer.setText(mDateFormat.format(listdata.get(position).getFechaMuestra()));
        if (listdata.get(position).getTomoMuestra().equalsIgnoreCase(Constants.YESKEYSND)) {
            holder.textViewName.setText(holder.context.getString(R.string.code) + ": " + muestraDTO.getCodigoMx() + " - " + holder.context.getString(R.string.volumen) + ": " + muestraDTO.getVolumen() + holder.context.getString(R.string.ml));
        } else {
            holder.textViewName.setText(listdata.get(position).getRazonNoToma() + (listdata.get(position).getOtraRazonNoToma() != null ? "-"+listdata.get(position).getOtraRazonNoToma() : ""));
        }*/

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        public ImageView imageView;
        public TextView textViewIdent;
        public TextView textViewDer;
        public TextView textViewName;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            this.imageView.setVisibility(View.GONE);
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);
        }
    }
}
