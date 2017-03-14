package com.baluobo.home.actions;

import com.baluobo.app.flux.Action;
import com.baluobo.home.model.Declaration;

import java.util.ArrayList;

/**
 * desc:
 * Created by:chenliliang
 * Created on:16/5/24.
 */
public class DeclarationAction extends Action<ArrayList<Declaration>> {
    private ArrayList<Declaration> data;
    public static final String ACTION_REQUEST_DECLARATION_SUCCESS = "action_request_declaration_success";
    public static final String BOUND_DATA_DECLARATION = "bound_data_declarations";
    public DeclarationAction(String type, ArrayList<Declaration> data) {
        super(type, data);
        this.data = data;
    }
    public DeclarationAction(String type){
        super(type, null);
    }
}
