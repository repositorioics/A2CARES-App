package ni.org.ics.a2cares.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.TelefonoContacto;
import ni.org.ics.a2cares.app.domain.puntos.PuntoCandidato;
import ni.org.ics.a2cares.app.ui.activities.enterdata.DescartarPuntoCandidatoActivity;
import ni.org.ics.a2cares.app.ui.activities.maps.CoordenadasMapActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 21/5/2021.
 */
public class PuntosCandidatosListAdapter extends RecyclerView.Adapter<PuntosCandidatosListAdapter.ViewHolder> {

    //private Activity activity;
    private List<PuntoCandidato> listdata;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public PuntosCandidatosListAdapter(List<PuntoCandidato> listdata) {
        //this.activity = activity;
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
        final PuntoCandidato puntoCandidato = listdata.get(position);
        holder.textViewIdent.setText(puntoCandidato.getCodigo().toString());
        holder.textViewDer.setText(mDateFormat.format(puntoCandidato.getRecordDate()));
        holder.textViewName.setText(listdata.get(position).getLatitud() + " , "+ listdata.get(position).getLongitud());
        holder.imageView.setImageResource(R.mipmap.ic_gps);
        holder.puntoCandidato = puntoCandidato;
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public PuntoCandidato puntoCandidato;
        private final Context context;
        public ImageView imageView;
        public TextView textViewIdent;
        public TextView textViewDer;
        public TextView textViewName;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            context = itemView.getContext();
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            //this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);

            relativeLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle(context.getString(R.string.candidatePointsContextMenuTitle, puntoCandidato.getCodigo().toString()));
            contextMenu.add(0, view.getId(), 0, R.string.discard).setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent i = new Intent(context,
                                    DescartarPuntoCandidatoActivity.class);
                            Bundle arguments = new Bundle();
                            arguments.putSerializable(Constants.PUNTO_GPS, puntoCandidato);
                            i.putExtras(arguments);
                            context.startActivity(i);
                            ((Activity)context).finish();
                            return true;
                        }
                    }
            );//groupId, itemId, order, title
            contextMenu.add(0, view.getId(), 1, R.string.locate_point).setOnMenuItemClickListener(
                    new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            Intent i = new Intent(context,
                                    CoordenadasMapActivity.class);
                            Bundle arguments = new Bundle();
                            arguments.putSerializable(Constants.PUNTO_GPS, puntoCandidato);
                            i.putExtras(arguments);
                            context.startActivity(i);
                            //do what u want
                            return true;
                        }
                    }
            );
        }


    }
}
