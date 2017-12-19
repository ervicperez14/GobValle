package com.ervic.mac.gobvalle.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ervic.mac.gobvalle.FontAwesome;
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
    Typeface iconFont;
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
        holder.text_url.setText(elementos_menu.get(position).link);
        iconFont = Typeface.createFromAsset(activity.getAssets(), "fontawesome-webfont.ttf" );

        if(elemento.icon == "perfil" || elemento.icon == "sesion") {
            holder.img_icono.setImageDrawable(activity.getResources().getDrawable(R.drawable.icono_usuario));
            holder.text_title.setText("   "+elemento.title);
            holder.text_titulo.setVisibility(View.GONE);
        }else if(elemento.icon == "atencion"){
            holder.img_icono.setImageDrawable(activity.getResources().getDrawable(R.drawable.icono_personas));
            holder.text_title.setText("   "+elemento.title);
            holder.text_titulo.setVisibility(View.GONE);
        }else if(elemento.icon == "cerrar"){
            holder.img_icono.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_exit_to_app_black_24dp));
            holder.text_title.setText("   "+elemento.title);
            holder.text_titulo.setVisibility(View.GONE);
        }else{
            holder.img_icono.setVisibility(View.GONE);
            holder.text_titulo.setText(Html.fromHtml("&#x"+elementos_menu.get(position).icon +";"));
            holder.text_title.setText("   "+elemento.title);
            holder.text_titulo.setTypeface(iconFont);
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
        TextView text_url;
        TextView text_title;

        public ViewHolder(View itemView) {
            super(itemView);
            img_icono = (ImageView) itemView.findViewById(R.id.icono_informacion);
            text_titulo = (TextView) itemView.findViewById(R.id.texto_informacion);
            text_url = (TextView) itemView.findViewById(R.id.url_información);
            text_title = (TextView) itemView.findViewById(R.id.texto_title);

        }

    }

}
