package com.ervic.mac.gobvalle.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ervic.mac.gobvalle.Models.Elemento;
import com.ervic.mac.gobvalle.Models.leftMenu;
import com.ervic.mac.gobvalle.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ervic on 11/22/17.
 */

public class AdapterInformacion extends RecyclerView.Adapter<AdapterInformacion.ViewHolder> {

    List<leftMenu> elementos_menu;
    Context activity;

    public AdapterInformacion(Context context, List<leftMenu> datos){
        this.elementos_menu = datos;
        this.activity = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_recycler_lateral, parent,false);
        ViewHolder  viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        leftMenu elemento = elementos_menu.get(position);
        holder.text_titulo.setText(elementos_menu.get(position).title);
        if(elemento.icon == "perfil") {
            holder.img_icono.setImageDrawable(activity.getResources().getDrawable(R.drawable.icono_usuario));
        }else if(elemento.icon == "atencion"){
            holder.img_icono.setImageDrawable(activity.getResources().getDrawable(R.drawable.icono_personas));
        }else{
            try {
                Picasso.with(this.activity.getApplicationContext()).load(this.activity.getResources().getString(R.string.server) + ((String) elemento.icon)).placeholder((int) R.drawable.icono_aviso).error((int) R.drawable.icono_aviso).into(holder.img_icono);
            } catch (Exception e) {
                Log.d("Error cargar Imagen", e.toString());
            }
        }

    }

    @Override
    public int getItemCount() {
        if(elementos_menu == null)
            return  0;
        else return elementos_menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icono;
        TextView text_titulo;


        public ViewHolder(View itemView) {
            super(itemView);
            img_icono = (ImageView) itemView.findViewById(R.id.icono_informacion);
            text_titulo = (TextView) itemView.findViewById(R.id.texto_informacion);
        }
    }
}
