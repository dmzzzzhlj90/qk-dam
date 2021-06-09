package com.qk.dam.sqlloader;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;
import org.apache.commons.io.IOUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.util.BatchTask.batchTask;
import static com.qk.dam.sqlloader.util.DownloadFile.*;

public class DmSqlLoaderTest {
    private static final Log LOG = LogFactory.get("文件下载");

    public static void main(String[] args) throws Exception {
        List<PiciTaskVO> piciTasks = DmSqlLoader.getPiciTaskNoLocalFile();

        List<FutureTask<Integer>> futureTasks = batchTask(piciTasks, (piciTaskVO) -> {
            //执行下载任务
            downloadFileFromUrl(LongGovConstant.HOST_ALIYUN_OSS + piciTaskVO.getOssPath()
                    , LongGovConstant.LOCAL_FILES_PATH,
                    (fileUrl) -> {
                        // 下载完成的文件
                        // piciTaskVO 执行批次日志状态保存

                    });
            return 1;
        });

        int sum = futureTasks.stream().mapToInt(t -> {
            try {
                return t.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }).sum();

        if (sum == futureTasks.size()) {
            LOG.info("批次【{}】所有文件下载完毕", 35);
        }

        for (FutureTask<Integer> futureTask : futureTasks) {
            Integer state = futureTask.get();
            System.out.println(state);
        }

    }


}
