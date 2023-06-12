package com.example.cloud.dao;

import com.example.cloud.entity.TracksInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;

@Mapper
public interface TracksMapper {
    TracksInfo selectById(int id);
    TracksInfo[] selectByUuid(String uuid);
    TracksInfo[] selectOneMain(String[] uuids);
    TracksInfo[] selectOneSub(@Param("t") BigInteger t, @Param("uuids") String[] uuids, @Param("foundId") String[] foundId, @Param("tancIds") String[] tancIds);
    TracksInfo[] selectTwoMain(@Param("t") BigInteger t, @Param("foundUuid") String[] foundUuid, @Param("treeIds") String[] treeIds);
    TracksInfo[] selectThreeMain(String uuid);
    TracksInfo[] selectThreeSub(String[] uuids, BigInteger t, String[] tancIds);
    TracksInfo[] selectByTreeId(String treeId);
    int insertTracks(TracksInfo tracksInfo);
}
