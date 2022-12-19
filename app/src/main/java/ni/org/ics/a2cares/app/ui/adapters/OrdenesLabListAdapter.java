package ni.org.ics.a2cares.app.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.medico.OrdenLaboratorio;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.dto.MuestraDTO;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 9/7/2021.
 */
public class OrdenesLabListAdapter extends RecyclerView.Adapter<OrdenesLabListAdapter.ViewHolder> {

    private List<OrdenLaboratorio> listdata;
    private List<MessageResource> catTipoMuestra;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    // RecyclerView recyclerView;
    public OrdenesLabListAdapter(List<OrdenLaboratorio> listdata, List<MessageResource> catTipoMuestra) {
        this.listdata = listdata;
        this.catTipoMuestra = catTipoMuestra;
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
        final OrdenLaboratorio ordenLaboratorio = listdata.get(position);
        holder.textViewIdent.setText(ordenLaboratorio.getParticipante().getCodigoNombreCompleto());
        holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        holder.textViewIdent.setTextColor(holder.context.getResources().getColor(R.color.blue_primary));
        holder.textViewDer.setText(mDateFormat.format(listdata.get(position).getFechaOrden()));
        holder.textViewName.setTextColor(holder.context.getResources().getColor(R.color.solid_red));
        if (listdata.get(position).getCategoria().equalsIgnoreCase("D") != true) {
            holder.textViewName.setText(String.format(holder.context.getString(R.string.datos_orden), getCatDesc(listdata.get(position).getTipoMuestra()), mDateFormat.format(listdata.get(position).getFis()), mDateFormat.format(listdata.get(position).getFif())));
        }else {

            holder.textViewName.setText(String.format(holder.context.getString(R.string.datos_orden), getCatDesc(listdata.get(position).getTipoMuestra()), mDateFormat.format(listdata.get(position).getFis()), ("10/10/3000")));
        }
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


    private String getCatDesc(String key){
        for(MessageResource cat : catTipoMuestra) {
            if (cat.getCatKey().equalsIgnoreCase(key))
                return cat.getSpanish();
        }
        return "";
    }
}
