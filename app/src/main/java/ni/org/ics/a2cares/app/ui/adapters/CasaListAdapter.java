package ni.org.ics.a2cares.app.ui.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import ni.org.ics.a2cares.app.AbstractAsyncActivity;
import ni.org.ics.a2cares.app.R;
import ni.org.ics.a2cares.app.domain.core.Casa;
import ni.org.ics.a2cares.app.domain.core.Participante;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuCasaActivity;
import ni.org.ics.a2cares.app.ui.activities.menu.MenuParticipanteActivity;
import ni.org.ics.a2cares.app.utils.Constants;

/**
 * Created by Miguel Salinas on 21/5/2021.
 */
public class CasaListAdapter extends RecyclerView.Adapter<CasaListAdapter.ViewHolder> {

    private List<Casa> listdata;
    private boolean nuevoIngreso;
    private Activity activity;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MMM dd, yyyy");

    // RecyclerView recyclerView;
    public CasaListAdapter(List<Casa> listdata, boolean nuevoIngreso, Activity activity) {
        this.listdata = listdata;
        this.nuevoIngreso = nuevoIngreso;
        this.activity = activity;
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
        final Casa casa = listdata.get(position);
        holder.textViewIdent.setText(holder.context.getString(R.string.code) + ": " + casa.getCodigo());
        holder.textViewDer.setText(casa.getBarrio().getNombre() );
        holder.textViewName.setText(casa.getDireccion());
        holder.imageView.setImageResource(R.mipmap.ic_house);


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nuevoIngreso) {
                    Intent i = new Intent(holder.context,
                            MenuCasaActivity.class);
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constants.CASA, casa);
                    i.putExtras(arguments);
                    //i.putExtra(ConstantsDB.VIS_EXITO, false);
                    holder.context.startActivity(i);
                } else {
                     AlertDialog alertDialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.context);
                    builder.setTitle(holder.context.getString(R.string.confirm));
                    builder.setMessage(holder.context.getString(R.string.select)+"\n"+holder.context.getString(R.string.code)+": " + casa.getCodigo() + " " + casa.getNombre1JefeFamilia() + " " + casa.getApellido1JefeFamilia());
                    builder.setPositiveButton(holder.context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            seleccionarCasa(casa);
                        }
                    });
                    builder.setNegativeButton(holder.context.getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    private void seleccionarCasa(Casa mCasa){
        Intent intent1 = new Intent();
        intent1.putExtra("CODE_RESULT", mCasa.getCodigo().toString());
        activity.setResult(Activity.RESULT_OK, intent1);
        activity.finish();
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
            this.textViewIdent = (TextView) itemView.findViewById(R.id.identifier_text);
            this.textViewDer = (TextView) itemView.findViewById(R.id.der_text);
            this.textViewName = (TextView) itemView.findViewById(R.id.name_text);

            relativeLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        }
    }


}
