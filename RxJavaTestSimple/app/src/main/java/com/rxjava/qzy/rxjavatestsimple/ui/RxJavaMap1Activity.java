package com.rxjava.qzy.rxjavatestsimple.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rxjava.qzy.rxjavatestsimple.R;
import com.rxjava.qzy.rxjavatestsimple.base.BaseActivity;
import com.rxjava.qzy.rxjavatestsimple.bean.Course;
import com.rxjava.qzy.rxjavatestsimple.bean.Student;
import com.rxjava.qzy.rxjavatestsimple.utils.LauncherManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/13
 *
 * 类描述：变换（一）
 */

public class RxJavaMap1Activity extends BaseActivity{
    public static String TAG = "RxJavaMap1Activity----->";

    @Bind(R.id.btn_map_print_name)
    Button btn_map_print_name;
    @Bind(R.id.btn_print_name_course)
    Button btn_print_name_course;
    @Bind(R.id.btn_print_flatMap)
    Button btn_print_flatMap;

    private Student[] students = new Student[3];

    public static void startFrom(Activity context) {
        Intent intent = new Intent(context, RxJavaMap1Activity.class);
        LauncherManager.launcher.launch(context, intent);
    }
    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map_first;
    }

    @Override
    protected void onViewCreated() {
        for(int i=0;i<3;i++){
            Student student = new Student("Tom");
            List<Course> courses = new ArrayList<Course>();
            courses.add(new Course("C++"));
            courses.add(new Course("Java"));
            courses.add(new Course("PHP"));
            student.courseList = courses;
            students[i] = student;
        }
    }
    @OnClick({R.id.btn_map_print_name,R.id.btn_print_name_course,R.id.btn_print_flatMap})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_map_print_name:
                Subscriber<String> subscriber = new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG,s);
                    }
                };
                Observable.from(students)
                        .map(new Func1<Student, String>() {
                            @Override
                            public String call(Student student) {
                                return student.name;
                            }
                        })
                        .subscribe(subscriber);
                break;
            case R.id.btn_print_name_course:
                Subscriber<Student> _subscriber = new Subscriber<Student>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Student student) {
                        List<Course> courses = student.courseList;
                        for (int i = 0; i < courses.size(); i++) {
                            Course course = courses.get(i);
                            Toast.makeText(getActivity(),course.courseName,Toast.LENGTH_SHORT).show();
                            Log.i(TAG, course.courseName);
                        }
                    }
                };
                Observable.from(students).subscribe(_subscriber);
                break;
            case R.id.btn_print_flatMap:
                Subscriber<Course> subscriber1 = new Subscriber<Course>() {
                    @Override
                    public void onNext(Course course) {
                        Log.i(TAG, course.courseName);
                    }
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                };
                Observable.from(students)
                        .flatMap(new Func1<Student, Observable<Course>>() {
                            @Override
                            public Observable<Course> call(Student student) {
                                return Observable.from(student.courseList);
                            }
                        })
                        .subscribe(subscriber1);
                break;
        }
    }

}
