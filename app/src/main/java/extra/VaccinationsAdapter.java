package extra;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titiit.ProjetCamp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class VaccinationsAdapter extends RecyclerView.Adapter<VaccinationsAdapter.ViewHolder> {
    DBhelper dBhelper;
    private List<Vaccination> vaccinations;
    Context ctx;

    public VaccinationsAdapter(List vaccs, Context ctx){
        this.vaccinations = vaccs;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View vaccinsationsItem = inflater.inflate(R.layout.vaccination_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(vaccinsationsItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Vaccination vaccination = vaccinations.get(position);
        holder.vaccin.setText(vaccination.getVaccin().getNom_vaccin());
        holder.date.setText(vaccination.getDate_vaccination());
        this.dBhelper = new DBhelper(this.ctx);
        this.dBhelper.openDataBase();
        holder.moughataa.setText(this.dBhelper.getMoughataa(vaccination.getMoughataa().getId()).getMoughataaname());
        this.dBhelper.close();
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Item :"+vaccination.getId(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vaccinations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView vaccin;
        public TextView moughataa;
        public TextView date;
        public RelativeLayout relativeLayout;

        public ViewHolder(View v){
            super(v);
            this.vaccin = ((TextView) v.findViewById(R.id.vaccin));
            this.moughataa = ((TextView) v.findViewById(R.id.moughataaVaccination));
            this.date = ((TextView) v.findViewById(R.id.dateVaccination));
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        }
    }
}
