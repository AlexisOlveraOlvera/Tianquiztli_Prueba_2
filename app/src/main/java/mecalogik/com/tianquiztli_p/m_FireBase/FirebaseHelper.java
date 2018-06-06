package mecalogik.com.tianquiztli_p.m_FireBase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;

import mecalogik.com.tianquiztli_p.m_Model.Spacecraft;

/**
 * Created by Ryzen on 06/06/2018.
 */

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Spacecraft> spacecrafts = new ArrayList<>();

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Spacecraft spacecraft){

        if(spacecraft== null){
            saved=false;
        }else {
            try {

                db.child("Spacecrafts").push().setValue(spacecraft);
                saved=true;

            } catch (DatabaseException e){
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    // IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void  fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Spacecraft spacecraft=ds.getValue(Spacecraft.class);
            spacecrafts.add(spacecraft);
        }
    }

    //READ THEN RETURN ARRAYLIST
    public ArrayList<Spacecraft> retrieve(){

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  spacecrafts;
    }
}