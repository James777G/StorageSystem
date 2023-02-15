package org.maven.apache.service.message;

import org.maven.apache.mapper.MessageMapper;

/**
 * 这个接口是为了更新缓存的方法 因为有两个lists一个收藏message一个没有收藏message
 */
public interface CachedMessageDataListService {
    void updateAllLists(MessageMapper MessageMapper, MessageManipulationService messageManipulationService);
}
