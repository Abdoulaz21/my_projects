package mySet;

import java.util.ArrayList;

public class IntegerSet {
    ArrayList<Integer> base_;

    public IntegerSet(){
        base_ = new ArrayList<>();
    }

    public void insert(Integer i) {
        if (this.has(i))
            return;
        int pos = 0;
        for (;pos < this.base_.size(); pos++) {
            if (this.base_.get(pos).compareTo(i) > 0) {
                this.base_.add(pos, i);
                break;
            }
        }
        if (pos == this.base_.size()) { this.base_.add(i); }
    }

    public void remove(Integer i){
        this.base_.remove(i);
    }

    public Boolean has(Integer i){
        for (var x : this.base_){
            if (x.equals(i)){ return true; }
        }
        return false;
    }

    public Boolean isEmpty(){
        return this.base_.isEmpty();
    }

    public Integer min(){
        return this.base_.get(0);
    }

    public Integer max(){
        int pos = this.base_.size() - 1;
        return this.base_.get(pos);
    }

    public Integer size(){
        return this.base_.size();
    }

    public static IntegerSet intersection(IntegerSet a, IntegerSet b){
        IntegerSet res = new IntegerSet();
        for (var x : a.base_){
            if (b.has(x)){ res.insert(x); }
        }
        return res;
    }

    public static IntegerSet union(IntegerSet a, IntegerSet b){
        IntegerSet res = new IntegerSet();
        res.base_ = a.base_;
        for (var x : b.base_){
            if (!res.has(x)){ res.insert(x); }
        }
        return res;
    }
}
