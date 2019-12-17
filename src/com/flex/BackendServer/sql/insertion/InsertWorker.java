package com.flex.BackendServer.sql.insertion;

import com.flex.BackendServer.sql.SQLHelper;
import com.flex.BackendServer.sql.Table;
import com.flex.BackendServer.sql.Worker;
import com.flex.BackendServer.utility.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsertWorker extends Worker {
    private String objectStringForm;

    public InsertWorker(Table table, Databasable databasableObject) {
        super(table);
        setObjectStringForm(SQLHelper.databasableToInsertionForm(databasableObject));
    }

    @Override
    public void run() {
        if (getStatement() == null)
            return;

        doInsertion();
        closeStatement();
    }

    private void doInsertion() {
        String sqlArg = "INSERT INTO " + getTable().name() + " VALUES " + getObjectStringForm();
        try {
            getStatement().execute(sqlArg);
        } catch (Exception e) {
            Utils.log("Failed to do insertion on table: " + getTable().name());
        }
    }
}