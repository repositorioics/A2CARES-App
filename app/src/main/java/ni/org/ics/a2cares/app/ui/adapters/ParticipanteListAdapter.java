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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ni.org.ics.a2cares.app.MyIcsApplication;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.database.EstudioDBAdapter;
import ni.org.ics.a2cares.app.database.constants.MainDBConstants;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.domain.core.MuestraEnfermo;

import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermo;
import ni.org.ics.a2cares.app.domain.laboratorio.RecepcionEnfermomessage;
import ni.org.ics.a2cares.app.domain.message.MessageResource;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaOrdenLaboratorioActivity;
import ni.org.ics.a2cares.app.ui.activities.enterdata.NuevaRecepcionEnfermoActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.ui.fragments.enterdata.NuevaRecepcionEnfermoFragment;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 21/5/2021.
 */
public class ParticipanteListAdapter extends RecyclerView.Adapter<ParticipanteListAdapter.ViewHolder> {

    private List<Participante> listdata;
    private boolean permisoVisita;
    private boolean desdeMedico;
    private static RecepcionEnfermo recepcionenfermo = new RecepcionEnfermo();
    private static RecepcionEnfermomessage recepcionenfermom = new RecepcionEnfermomessage();
    private EstudioDBAdapter estudiosAdapter;
    private boolean desdeLaboratorio;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private List<RecepcionEnfermo> listdata1;
    private List<RecepcionEnfermomessage> listdata2;
    private String dias;


    // RecyclerView recyclerView;
    public ParticipanteListAdapter(List<Participante> listdata,List<RecepcionEnfermomessage> listdata2, boolean permisoVisita, boolean desdeMedico, boolean desdeLaboratorio) {
        this.listdata = listdata;
        this.listdata2= listdata2;
        this.permisoVisita = permisoVisita;
        this.desdeMedico = desdeMedico;
        this.desdeLaboratorio = desdeLaboratorio;


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
        final Participante participante = listdata.get(position);
       // estudiosAdapter.open();



        holder.textViewIdent.setText(holder.context.getString(R.string.code) + ": " + listdata.get(position).getCodigo());
        holder.textViewDer.setText(mDateFormat.format(listdata.get(position).getFechaNac()));
        holder.textViewName.setText(listdata.get(position).getNombreCompleto());
        holder.textViewName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

            if (participante.getSexo().equalsIgnoreCase("F")) {
                holder.imageView.setImageResource(R.mipmap.ic_female);
            } else {
                holder.imageView.setImageResource(R.mipmap.ic_male);
            }
        if(participante.getProcesos().getPendienteMxTx().equalsIgnoreCase("1")) {
            if (listdata2.size() != 0) {
                for (int b=0;b < listdata2.size();b++) {
                    dias = listdata2.get(b).getPositivo();

                    if (Integer.parseInt(dias) < 14) {
                        holder.textViewConva1.setText(String.format(String.format(String.format(holder.context.getString(R.string.alerta_conva) + " --Días Conv.: " + dias + " --Fis.: " + mDateFormat.format(listdata2.get(b).getFis())))));
                    }
                    if (Integer.parseInt(dias) > 13 && Integer.parseInt(dias) < 46) {
                        holder.textViewConva.setText(holder.context.getString(R.string.alerta_conva) + " --Días Conv.: " + dias + " --Fis.: " + mDateFormat.format(listdata2.get(b).getFis()));
                    }
                }
            }else{
                dias = "0";
            }

        }else{
            holder.textViewConva.setText("");
        }

        if(participante.getProcesos().getRetirado().equals(1)) {
            holder.textViewConva.setText(holder.context.getString(R.string.alerta_retirado) + "");
        }

       // estudiosAdapter.close();
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (desdeMedico) {
                    Intent i = new Intent(holder.context,
                            NuevaOrdenLaboratorioActivity.class);
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.PARTICIPANTE, participante);
                    i.putExtras(arguments);
                    holder.context.startActivity(i);
                    ((Activity)holder.context).finish();
                } else if (desdeLaboratorio) {
                    Intent i = new Intent(holder.context,
                            NuevaRecepcionEnfermoActivity.class);
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.PARTICIPANTE, participante);
                    i.putExtras(arguments);
                    holder.context.startActivity(i);
                    ((Activity)holder.context).finish();
                }
                else {
                    Intent i = new Intent(holder.context,
                            MenuParticipanteActivity.class);
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.PARTICIPANTE, participante);
                    i.putExtras(arguments);
                    i.putExtra(Constants.VISITA_EXITOSA, !permisoVisita); //si no tiene permiso de visita, se va a asumir la visita como exitosa para no solicitar visita al momento de ingresar informacion
                    holder.context.startActivity(i);
                }
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
        public TextView textViewConva;
        public TextView textViewConva1;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.imageView = (ImageView) itemView.findViewById(R.id.image);
            //this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);
            this.textViewConva = (TextView) itemView.findViewById(R.id.alert_Conva);
            this.textViewConva1 = (TextView) itemView.findViewById(R.id.alert_Conva1);

            relativeLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }
}
