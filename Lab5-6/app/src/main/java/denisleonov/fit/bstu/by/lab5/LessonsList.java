package denisleonov.fit.bstu.by.lab5;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LessonsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<Lesson> lessons;
    private ArrayAdapter<Lesson> adapter;
    private List<Lesson> todayLessons;

    private String dayOfTheWeek;
    private int currentNumWeek;

    public LessonsList() {
        // Required empty public constructor
    }

    interface OnFragmentSendDataListener {
        void onSendData(Lesson lesson);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;

    @Override
    public void onAttach(Context context) { // Взаимодействуем с другим фрагментов через Activity
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonsList.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonsList newInstance(String param1, String param2) {
        LessonsList fragment = new LessonsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lessons_list, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            dayOfTheWeek = bundle.getString("day");
            currentNumWeek = bundle.getInt("week");
        }

        todayLessons = new ArrayList<>();


        lessons = JSONHelper.importFromJSON(getContext());
        if(lessons == null){
            lessons = new ArrayList<>();
        }



        for(Lesson lesson:lessons){
            if(lesson.Day.equals(dayOfTheWeek) && lesson.Week==currentNumWeek){
                todayLessons.add(lesson);
            }
        }
        ArrayAdapter<Lesson> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, todayLessons);
        ListView listView = view.findViewById(R.id.FSchedule);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        // Inflate the layout for this fragment

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                Lesson selectedItem = (Lesson)parent.getItemAtPosition(position);
                // Посылаем данные Activity
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });

        return view;
    }
}