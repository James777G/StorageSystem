package org.maven.apache.service.regulatory;

import org.maven.apache.email.Email;
import org.maven.apache.regulatory.Regulatory;

import java.util.List;

public interface RegulatoryDataManipulationService {

    List<List<Regulatory>> getPagedCacheList(List<Regulatory> emailList, int PageSize);

}
