package com.qinzi123.dao;

import com.qinzi123.dto.TableConfig;

import java.util.HashMap;
import java.util.List;

/**
 * @title: TableConfigDao
 * @package: com.qinzi123.dao
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
public interface TableConfigDao {

    List<TableConfig> findTableConfig();

    List<HashMap> findTableColumn();
}
