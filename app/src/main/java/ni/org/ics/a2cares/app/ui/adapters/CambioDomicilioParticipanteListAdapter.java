package ni.org.ics.a2cares.app.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.ui.activities.cambioDomicilio.CambioDomicilioParticipanteActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaOrdenLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaRecepcionEnfermoActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;

public class CambioDomicilioParticipanteListAdapter extends RecyclerView.Adapter<CambioDomicilioParticipanteListAdapter.ViewHolder>{
    private List<Participante> listdata;
    private boolean permisoVisita;
    private boolean desdeMedico;
    private boolean desdeLaboratorio;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public CambioDomicilioParticipanteListAdapter(List<Participante> listdata, boolean permisoVisita, boolean desdeMedico, boolean desdeLaboratorio) {
        this.listdata = listdata;
        this.permisoVisita = permisoVisita;
        this.desdeMedico = desdeMedico;
        this.desdeLaboratorio = desdeLaboratorio;
    }

    @Override
    public CambioDomicilioParticipanteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.complex_list_item, parent, false);
        CambioDomicilioParticipanteListAdapter.ViewHolder viewHolder = new CambioDomicilioParticipanteListAdapter.ViewHolder(listItem) {
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CambioDomicilioParticipanteListAdapter.ViewHolder holder, int position) {
        final Participante participante = listdata.get(position);
        holder.textViewIdent.setText(holder.context.getString(R.string.code) + ": " + listdata.get(position).getCodigo());
        holder.textViewDer.setText(mDateFormat.format(listdata.get(position).getFechaNac()));
        holder.textViewName.setText(listdata.get(position).getNombreCompleto());
        holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        if (participante.getSexo().equalsIgnoreCase("F")) {
            holder.imageView.setImageResource(R.mipmap.ic_female);
        } else {
            holder.imageView.setImageResource(R.mipmap.ic_male);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(holder.context,
                        CambioDomicilioParticipanteActivity.class);
                Bundle arguments = new Bundle();
                arguments.putSerializable(Constants.PARTICIPANTE, participante);
                i.putExtras(arguments);
                i.putExtra(Constants.VISITA_EXITOSA, !permisoVisita); //si no tiene permiso de visita, se va a asumir la visita como exitosa para no solicitar visita al momento de ingresar informacion
                holder.context.startActivity(i);
            }
        });
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
