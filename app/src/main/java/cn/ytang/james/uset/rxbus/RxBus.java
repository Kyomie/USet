package cn.ytang.james.uset.rxbus;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 *
 *
 * Created by James on 17/11/17.
 */

public class RxBus {

    private static final class RxBusHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    public static RxBus getInstance() {
        return RxBusHolder.INSTANCE;
    }

    private final ConcurrentHashMap<Object, List<Subject>> mSubjectMapper = new ConcurrentHashMap<>();

    private RxBus() {}

    /**
     * 注册事件源
     *
     * @param tag
     * @param cls
     * @return
     */
    @SuppressWarnings("UnusedParameters")
    public <T>Observable<T> register(@Nullable Object tag, @Nullable Class<T> cls) {
        List<Subject> subjectList = mSubjectMapper.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            mSubjectMapper.put(tag, subjectList);
        }
        Subject<T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    /**
     * 注册事件源
     *
     * @param tag
     * @return
     */
    public <T> Observable<T> register(@Nullable Object tag) {
        return register(tag, null);
    }

    /**
     * 取消监听
     *
     * @param tag
     * @param observable
     * @return
     */
    public RxBus unregister(@Nullable Object tag, @Nullable Observable<?> observable) {
        List<Subject> subjects = mSubjectMapper.get(tag);
        if (subjects != null) {
            subjects.remove((Subject) observable);
            if (isEmpty(subjects)) {
                mSubjectMapper.remove(tag);
            }
        }
        return this;
    }

    /**
     * 取消此tag所对应的所有监听
     *
     * @param tag
     * @return
     */
    public void unregisterAllForTag(@Nullable Object tag) {
        List<Subject> subjects = mSubjectMapper.get(tag);
        if (subjects != null) {
            mSubjectMapper.remove(tag);
        }
    }

    /**
     * 触发事件
     *
     * @param content
     */
    public void postWithClassNameAsTag(@Nullable Object content) {
        post(content.getClass().getName(), content);
    }

    /**
     * 触发事件
     *
     * @param tag
     * @param content
     */
    @SuppressWarnings("unchecked")
    public void post(@Nullable Object tag, @Nullable Object content) {
        List<Subject> subjectList = mSubjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    private static <T> boolean isEmpty(@Nullable Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

}
