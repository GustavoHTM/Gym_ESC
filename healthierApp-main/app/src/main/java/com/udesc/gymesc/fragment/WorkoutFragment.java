package com.udesc.gymesc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udesc.gymesc.R;
import com.udesc.gymesc.adapter.WorkoutAdapter;
import com.udesc.gymesc.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class WorkoutFragment extends Fragment {

    private RecyclerView recyclerView;
    private WorkoutAdapter adapter;
    private List<Workout> workoutList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        workoutList = new ArrayList<>();
        workoutList.add(new Workout("Treino de Força", "Um treino focado em aumento de força.", R.drawable.strength_workout));
        workoutList.add(new Workout("Treino Cardio", "Um treino focado em resistência cardiovascular.", R.drawable.cardio_workout));
        workoutList.add(new Workout("Treino Flexibilidade", "Um treino focado em melhorar a flexibilidade.", R.drawable.flexibility_workout));

        adapter = new WorkoutAdapter(workoutList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
