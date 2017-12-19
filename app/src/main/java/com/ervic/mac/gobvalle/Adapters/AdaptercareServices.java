package com.ervic.mac.gobvalle.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ervic.mac.gobvalle.Models.careServicesMenu;
import com.ervic.mac.gobvalle.R;

import java.util.List;

/**
 * Created by ervic on 11/28/17.
 */

public class AdaptercareServices extends RecyclerView.Adapter<AdaptercareServices.ViewHolder>{
    private List<careServicesMenu>  elementos_menu;
    private Context context;

    public AdaptercareServices(Context context, List<careServicesMenu> elementos_menu) {
        this.elementos_menu = elementos_menu;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_recycler_care,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(elementos_menu.get(position).title);
        holder.url.setText(elementos_menu.get(position).link);

        holder.imagen.setImageDrawable(context.getResources().getDrawable(R.drawable.icono_circulos));

    }

    @Override
    public int getItemCount() {
        if(elementos_menu == null)
            return  0;
        else return elementos_menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        TextView url;
        ImageView imagen;
        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.texto_informacion);
            url = (TextView) itemView.findViewById(R.id.url_información);
            imagen = (ImageView) itemView.findViewById(R.id.icono_informacion);
        }
    }
}
