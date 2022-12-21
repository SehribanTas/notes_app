package com.example.note;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

class   Adapter extends RecyclerView.Adapter<Adapter.CardViewTasarimNesneleriniTutucu> {

    private Context mcontext;
    private DbHelper dbHelper;
    private List<Tasks> TasksList;

    public Adapter(Context mcontext, ArrayList<Tasks> TasksList,DbHelper dbHelper) {
        this.mcontext = mcontext;
        this.TasksList = TasksList;
        this.dbHelper= dbHelper;
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card,parent,false);
        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.CardViewTasarimNesneleriniTutucu holder, int position) {
        final Tasks task=TasksList.get(position);
        holder.satirYazi.setText(task.getTask());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateintent= new Intent(mcontext,UpdateTask.class);
                updateintent.putExtra("task",task.getTask());
                updateintent.putExtra("task_id",task.getTask_id());
                mcontext.startActivity(updateintent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mcontext, holder.imageView);//PopMenu için bir nesne oluşturuldu ve constructorına görünmesini sağlayacak görsel nesne eklendi.
                popupMenu.getMenuInflater().inflate(R.menu.card_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.action_delete:
                                Snackbar.make(holder.imageView,"görev silinsin mi?", Snackbar.LENGTH_LONG).setAction("Evet", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new Tasksdao().deleteTask(dbHelper, task.getTask_id());

                                        TasksList = new Tasksdao().getTasks(dbHelper);

                                        notifyDataSetChanged();
                                    }
                                }).show();
                               return true;
                               default:
                                return false;
                        }
                    }
                });

                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return TasksList.size();
    }

    ////////////////////////
    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder{

       public TextView satirYazi;
       public CardView cardView;
       public ImageView imageView;

       public CardViewTasarimNesneleriniTutucu(View view){
           super(view);
           satirYazi = view.findViewById(R.id.satirYazi);
           cardView=view.findViewById(R.id.cardView);
           imageView=view.findViewById(R.id.imagemenu);

       }
    }
}
