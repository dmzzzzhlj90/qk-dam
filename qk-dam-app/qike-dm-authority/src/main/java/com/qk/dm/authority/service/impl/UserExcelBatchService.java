package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.util.MultipartFileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**用户列表
 * @author zys
 * @date 2022/3/4 15:49
 * @since 1.0.0
 */
@Service
public class UserExcelBatchService {
  private static final Log LOG = LogFactory.getLog("userExcelBatchService");
  private final BloomFilterServer bloomFilterServer;
  private final AtyUserService atyUserService;


  public UserExcelBatchService(BloomFilterServer bloomFilterServer,
      AtyUserService atyUserService) {
    this.bloomFilterServer = bloomFilterServer;
    this.atyUserService = atyUserService;
  }

  public List<AtyUserInputExceVO> saveUser(List<AtyUserInputExceVO> Userlist,
      String relame) {
    List<AtyUserInputExceVO> lsit = deal(Userlist,relame);
    saveAllUsers(Userlist,relame);
    LOG.info(Userlist.size()+"成功保存资源信息个数 【{}】");
    return lsit;

  }

  /**
   * 存储用户信息到keycloak
   * @param userlist
   * @param relame
   */
  private void saveAllUsers(List<AtyUserInputExceVO> userlist, String relame) {
    atyUserService.saveAllUsers(userlist,relame);
  }

  /**
   * 处理去重用户
   * @param userList
   * @param relame
   * @return
   */
  private List<AtyUserInputExceVO> deal(List<AtyUserInputExceVO> userList,
      String relame) {
    List<AtyUserInputExceVO> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(userList)){
      Iterator<AtyUserInputExceVO> iterator = userList.iterator();
      while (iterator.hasNext()){
        AtyUserInputExceVO atyUserInputExceVO = iterator.next();
        //todo 加入操作人员id
        String key = MultipartFileUtil.getUserExcelKey(atyUserInputExceVO,relame);
        if (bloomFilterServer.getFilter()!=null){
          boolean b = bloomFilterServer.getFilter().mightContain(key);
          if (b){
            list.add(atyUserInputExceVO);
            iterator.remove();
          }else {
            bloomFilterServer.getFilter().put(key);
          }
        }
      }
    }
    return list;

  }
}