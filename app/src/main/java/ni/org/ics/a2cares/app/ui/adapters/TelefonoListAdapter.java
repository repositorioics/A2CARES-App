package ni.org.ics.a2cares.app.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 21/5/2021.
 */
public class TelefonoListAdapter extends RecyclerView.Adapter<TelefonoListAdapter.ViewHolder> {

    private List<TelefonoContacto> listdata;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public TelefonoListAdapter(List<TelefonoContacto> listdata) {
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
        final TelefonoContacto participante = listdata.get(position);
        holder.textViewIdent.setText(participante.getParticipante().getNombreCompleto());
        holder.textViewDer.setText(mDateFormat.format(participante.getRecordDate()));
        holder.textViewName.setText(listdata.get(position).getNumero());
        if (participante.getTipo().equalsIgnoreCase(Constants.TIPO_TEL_CEL)) {
            holder.imageView.setImageResource(R.mipmap.ic_smartphone);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_phone);
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
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            //this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);

            relativeLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
