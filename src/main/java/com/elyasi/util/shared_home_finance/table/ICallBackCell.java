package com.elyasi.util.shared_home_finance.table;

public interface ICallBackCell<TDataModel> {
    void call(TDataModel _input, int _rowIndex);
}
