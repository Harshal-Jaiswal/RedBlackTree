package com.example.hpjaiswal.redblacktree;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> keys=new ArrayList();
    TextView key,dkey,in,pre,post;
    rbt tr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        key=(TextView)findViewById(R.id.key);
        dkey=(TextView)findViewById(R.id.dkey);
        in=(TextView)findViewById(R.id.inorder);
        pre=(TextView)findViewById(R.id.preorder);
        post=(TextView)findViewById(R.id.postorder);
        tr=new rbt();
    }

    public void insert(View v){
        if(key.getText().length()<=0){
            Toast.makeText(this,"enter elements",Toast.LENGTH_SHORT).show();
        }else{
        int a=Integer.valueOf(key.getText().toString());
        if(keys.contains(a)){
            Toast.makeText(this,"Element alredy present",Toast.LENGTH_SHORT).show();
            key.setText("");
            key.setHint("Insert values");
        }else{
            keys.add(a);
            tr.insert(a);
            tr.display();
            key.setText("");
            key.setHint("Insert values");
        }
    }}

    public void delete(View v){
        if(dkey.getText().length()<=0){
            Toast.makeText(this,"enter element to be deleted",Toast.LENGTH_SHORT).show();
        }else {
            int a=Integer.valueOf(dkey.getText().toString());if(!keys.contains(a)){
                Toast.makeText(this,"Element not present",Toast.LENGTH_SHORT).show();
            }else{
                keys.remove(keys.indexOf(a));
                tr=new rbt();
                for(int i=0;i<keys.size();i++){
                    tr.insert(keys.get(i));
                }
                tr.display();

            }
        }

        dkey.setText("");
        dkey.setHint("Enter element to be deleted");
    }


    /**
     *
     * @author HP JAISWAL
     */
    class rbtree {

        int data;
        String col;
        rbtree leftchild;
        rbtree rightchild;


        public rbtree(int ele, rbtree lc, rbtree rc) {
            this.data = ele;
            this.col = "r";
            this.leftchild = lc;
            this.rightchild = rc;
        }

        public void rbtreechild(rbtree lc, rbtree rc) {
            leftchild = lc;
            rightchild = rc;
        }

        public rbtree(int ele) {
            //if (data == 0) {
            this.data = ele;
            this.col = "r";
            leftchild = null;
            rightchild = null;
        }

    }

    class rbt {

        rbtree parent;
        rbtree node;

        public rbt() {
            node = new rbtree(0, null, null);
            parent = null;
        }

        public void insert(int ele) {
            rbtree curr, curr1;
            if (parent == null) {
                parent = new rbtree(ele, null, null);
                parent.col = "b";
                curr = parent;
            } else {
                node = new rbtree(ele, null, null);
                curr = parent;
                while (true) {

                    if (curr.data > ele) {
                        curr1 = curr.leftchild;
                        if (curr1 == null) {
                            curr.rbtreechild(node, curr.rightchild);
                            check(node);
                            break;
                        }
                        curr = curr1;
                    } else {
                        curr1 = curr.rightchild;
                        if (curr1 == null) {
                            curr.rbtreechild(curr.leftchild, node);
                            check(node);
                            break;
                        }
                        curr = curr1;
                    }

                }

            }
        }

        private void check(rbtree r) {
            parent.col = "b";
            rbtree child = r, fthr, uncle, grandpa;
            if (child != parent && (fthr = father(parent, child)) != null && (grandpa = father(parent, fthr)) != null) {
                if (child.col.matches("r") && fthr.col.matches("r")) {
                    uncle = brother(parent, fthr);
                    if (uncle != null && uncle.col.matches("r")) {
                        fthr.col = "b";
                        grandpa.col = "r";
                        uncle.col = "b";
                        check(grandpa);
                    } else {
                        if (uncle == null || uncle.col.matches("b")) {
                            if (fthr == grandpa.leftchild && child == fthr.rightchild) {
                                fthr.rightchild = child.leftchild;
                                child.leftchild = fthr;
                                grandpa.leftchild = child;
                                fthr = grandpa.leftchild;
                                child = fthr.leftchild;
                            } else {
                                if (fthr == grandpa.rightchild && child == fthr.leftchild) {
                                    fthr.leftchild = child.rightchild;
                                    child.rightchild = fthr;
                                    grandpa.rightchild = child;
                                    fthr = grandpa.rightchild;
                                    child = fthr.rightchild;
                                }
                            }

                            grandpa.col = "r";
                            fthr.col = "b";
                            if (grandpa == parent) {
                                if (fthr == grandpa.rightchild) {
                                    grandpa.rightchild = fthr.leftchild;
                                    fthr.leftchild = grandpa;
                                    parent = fthr;
                                } else {
                                    if (fthr == grandpa.leftchild) {
                                        grandpa.leftchild = fthr.rightchild;
                                        fthr.rightchild = grandpa;
                                        parent = fthr;
                                    }
                                }
                            } else {
                                rbtree pargrandpa = father(parent, grandpa);
                                if (pargrandpa.rightchild == grandpa) {
                                    if (grandpa.rightchild == fthr) {
                                        grandpa.rightchild = fthr.leftchild;
                                        fthr.leftchild = grandpa;
                                        pargrandpa.rightchild = fthr;
                                    } else {
                                        grandpa.leftchild = fthr.rightchild;
                                        fthr.rightchild = grandpa;
                                        pargrandpa.rightchild = fthr;
                                    }
                                } else {
                                    if (pargrandpa.leftchild == grandpa) {
                                        if (grandpa.leftchild == fthr) {
                                            grandpa.leftchild = fthr.rightchild;
                                            fthr.rightchild = grandpa;
                                            pargrandpa.leftchild = fthr;
                                        } else {
                                            grandpa.rightchild = fthr.leftchild;
                                            fthr.leftchild = grandpa;
                                            pargrandpa.leftchild= fthr;
                                        }
                                    }
                                }
                            }

                        }
                        check(grandpa);
                    }
                }
            }
            parent.col = "b";
        }

        private rbtree uncle(rbtree p, rbtree unc) {
            return null;
        }

        private rbtree father(rbtree p, rbtree son) {
            if (p != null) {
                if (son == p.leftchild || son == p.rightchild) {
                    return p;
                } else {
                    if (son.data < p.data) {
                        return father(p.leftchild, son);
                    } else {
                        return father(p.rightchild, son);
                    }

                }
            }
            return null;
        }

        private rbtree brother(rbtree p, rbtree bro) {
            if (p != null) {
                if (bro == p.leftchild) {
                    return p.rightchild;
                } else {
                    if (bro == p.rightchild) {
                        return p.leftchild;
                    } else {
                        if (bro.data < p.data) {
                            return brother(p.leftchild, bro);
                        } else {
                            return brother(p.rightchild, bro);
                        }
                    }
                }
            }
            return null;
        }

        public void display() {
            //System.out.println("\n preorder");
            pre.setText("Preorder :\n");
            preorder();
            in.setText("Inorder :\n");
            //System.out.println("\n inorder");
            inorder();
            //System.out.println("\n postorder");
            post.setText("Postorder :\n");
            postorder();
        }

        public void preorder() {
            preorder(parent);
        }

        private void preorder(rbtree r) {
            if (r != null) {
                //System.out.print(r.data + "" + r.col + " ");
                pre.setText(pre.getText().toString()+" "+r.data+""+r.col);
                preorder(r.leftchild);
                preorder(r.rightchild);
            }
        }

        public void inorder() {
            inorder(parent);
        }

        private void inorder(rbtree r) {
            if (r != null) {
                inorder(r.leftchild);
                //System.out.print(r.data + "" + r.col + " ");
                in.setText(in.getText().toString()+" "+r.data+""+r.col);
                inorder(r.rightchild);
            }
        }

        public void postorder() {
            postorder(parent);
        }

        private void postorder(rbtree r) {
            if (r != null) {
                postorder(r.leftchild);

                postorder(r.rightchild);
                post.setText(post.getText().toString()+" "+r.data+""+r.col);
                //System.out.print(r.data + "" + r.col + " ");
            }
        }
    }
}
