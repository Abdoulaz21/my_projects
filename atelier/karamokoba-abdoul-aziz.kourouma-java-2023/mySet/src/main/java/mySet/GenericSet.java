package mySet;

import java.util.ArrayList;

public class GenericSet<Type_T extends Comparable<Type_T>>{
    ArrayList<Type_T> base_;

    public GenericSet(){
        base_ = new ArrayList<Type_T>();
    }

    public void insert(Type_T i){
        int pos = 0;
        for (;pos < this.base_.size(); pos++) {
            if (this.base_.get(pos).compareTo(i) > 0) {
                this.base_.add(pos, i);
                break;
            }
        }
        if (pos == this.base_.size()) { this.base_.add(i); }
    }

    public void remove(Type_T i){
        this.base_.remove(i);
    }

    public Boolean has(Type_T i){
        return this.base_.contains(i);
    }

    public Boolean isEmpty(){
        return this.base_.isEmpty();
    }

    public Type_T min(){
        return this.base_.get(0);
    }

    public Type_T max(){
        int pos = this.base_.size() - 1;
        return this.base_.get(pos);
    }

    public Integer size(){
        return this.base_.size();
    }

    public static GenericSet intersection(GenericSet a, GenericSet b){
        GenericSet res = new GenericSet();
        for (var x : a.base_){
            if (b.has((Comparable) x)){ res.insert((Comparable) x); }
        }
        return res;
    }

    public static GenericSet union(GenericSet a, GenericSet b){
        GenericSet res = new GenericSet();
        res.base_ = a.base_;
        for (var x : b.base_){
            if (!res.has((Comparable) x)){ res.insert((Comparable) x); }
        }
        return res;
    }
}
