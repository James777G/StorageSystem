package org.maven.apache.service.email;

import org.maven.apache.email.Email;

import java.util.List;

public interface EmailCachedDataManipulationService {

    List<List<Email>> getPagedCacheList(List<Email> emailList, int PageSize);

}
