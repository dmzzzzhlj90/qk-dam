package com.qk.dam.antlr.sqlparser;

import com.qk.dam.antlr.sqlparser.mysql.MySqlContext;
import com.qk.dam.antlr.sqlparser.mysql.listener.InsertSpecificationSqlListener;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlLexer;
import com.qk.dam.antlr.sqlparser.mysql.parser.MySqlParser;
import com.qk.dam.antlr.sqlparser.mysql.stream.ANTLRNoCaseStringStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class TestAA {
    public static void main(String[] args) {
        String sql = "INSERT INTO `dataplus_b2b` VALUES " +
                "(75820967,37025710,'企业供需库','',NULL,'生产商','','深振WERP企业管理系统软件寻四川代理商，云存储、远程备份销毁，操作简便。','http://sczhdryxgs.cn.gongxuku.com/',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'加工 / 电工电气产品加工; 电工电气 / 插头; 电工电气 / 电动机; 电工电气 / 高压电器; 电工电气 / 配电输电设备; 电工电气 / 电线、电缆',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'http://sczhdryxgs.cn.gongxuku.com/introduce/','2021-07-01 01:49:18')" +
                ",(75820968,1659819,'万国企业网','五里桥二街1号院8号楼925',NULL,'其它','公司经营范围涉及：国际铁路联运、海铁联运、海运出口、海运进口、过境运输、仓储及国内分拨等领域，公司拥有一批从事国际铁路联运多年的员工队伍。','北京渠成国际货运代理有限公司是专业的国际铁路运输/国际多式联运服务提供商，与铁道部货运部门以及各大火车站建立了非常好的合作关系，是中国铁路总公司一级代理，在天津、常州、青岛、连云港、上海、宁波、厦门、西安、广州、深圳等车站设有办事处和工作人员，在哈萨克斯坦、乌兹别克斯坦、吉尔吉斯斯坦、塔吉克斯坦、土库曼斯坦、俄罗斯、蒙古国有办事处及长期合作的代理公司，可为客户提供覆盖俄罗斯、哈萨克斯坦、乌兹别克斯坦、塔吉克斯坦、吉尔吉斯斯坦、蒙古等国家集装箱、整车以及工程物流的进出口服务及完善的全程跟踪服务。 公司经营范围涉及：国际铁路联运、海铁联运、海运出口、海运进口、过境运输、仓储及国内分拨等领域，公司拥有一批从事国际铁路联运多年的员工队伍。具有丰富的铁路国际联运操作经验，既可以为客户提供优质高效的物流服务，也可以根据客户的特殊需求为客户制定合理的物流方案，对物流成本实施持续有效的控制和管理。 经过多年的积淀，我司与多家船公司、中国铁路、俄罗斯及中亚各国铁路等建立了长期、友好、稳定的合作关系，具有完善的服务网络及优惠的运价体系，可满足不同客户不同货物出运的各种需求。先后完成独联体国家数十个工程项目的物流服务, 涉及电力工程、工业工程、交通工程、农业工程等多个领域。为客户提供从工厂包装开始到目的地清关门到门的物流服务，凭借领先的物流服务模式和专业的团队作业以及丰富的项目运作经验，为中国企业打造一条独联体国家运输的便捷通道。','http://www.mrktrans.cn/; http://carlylin.cn.trustexporter.com/',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'物流 / 物流软件 / 货代管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'http://carlylin.cn.trustexporter.com/introduce/','2021-07-01 01:49:18')," +
                "(75820967,37025710,'企业供需库','',NULL,'生产商','','深振WERP企业管理系统软件寻四川代理商，云存储、远程备份销毁，操作简便。','http://sczhdryxgs.cn.gongxuku.com/',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'加工 / 电工电气产品加工; 电工电气 / 插头; 电工电气 / 电动机; 电工电气 / 高压电器; 电工电气 / 配电输电设备; 电工电气 / 电线、电缆',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'http://sczhdryxgs.cn.gongxuku.com/introduce/','2021-07-01 01:49:18')" +
                ",(75820968,1659819,'万国企业网','五里桥二街1号院8号楼925',NULL,'其它','公司经营范围涉及：国际铁路联运、海铁联运、海运出口、海运进口、过境运输、仓储及国内分拨等领域，公司拥有一批从事国际铁路联运多年的员工队伍。','北京渠成国际货运代理有限公司是专业的国际铁路运输/国际多式联运服务提供商，与铁道部货运部门以及各大火车站建立了非常好的合作关系，是中国铁路总公司一级代理，在天津、常州、青岛、连云港、上海、宁波、厦门、西安、广州、深圳等车站设有办事处和工作人员，在哈萨克斯坦、乌兹别克斯坦、吉尔吉斯斯坦、塔吉克斯坦、土库曼斯坦、俄罗斯、蒙古国有办事处及长期合作的代理公司，可为客户提供覆盖俄罗斯、哈萨克斯坦、乌兹别克斯坦、塔吉克斯坦、吉尔吉斯斯坦、蒙古等国家集装箱、整车以及工程物流的进出口服务及完善的全程跟踪服务。 公司经营范围涉及：国际铁路联运、海铁联运、海运出口、海运进口、过境运输、仓储及国内分拨等领域，公司拥有一批从事国际铁路联运多年的员工队伍。具有丰富的铁路国际联运操作经验，既可以为客户提供优质高效的物流服务，也可以根据客户的特殊需求为客户制定合理的物流方案，对物流成本实施持续有效的控制和管理。 经过多年的积淀，我司与多家船公司、中国铁路、俄罗斯及中亚各国铁路等建立了长期、友好、稳定的合作关系，具有完善的服务网络及优惠的运价体系，可满足不同客户不同货物出运的各种需求。先后完成独联体国家数十个工程项目的物流服务, 涉及电力工程、工业工程、交通工程、农业工程等多个领域。为客户提供从工厂包装开始到目的地清关门到门的物流服务，凭借领先的物流服务模式和专业的团队作业以及丰富的项目运作经验，为中国企业打造一条独联体国家运输的便捷通道。','http://www.mrktrans.cn/; http://carlylin.cn.trustexporter.com/',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'物流 / 物流软件 / 货代管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'http://carlylin.cn.trustexporter.com/introduce/','2021-07-01 01:49:18')";

        MySqlLexer lexer = new MySqlLexer(new ANTLRNoCaseStringStream(sql));

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        MySqlParser parser = new MySqlParser(tokenStream);

        MySqlParser.RootContext rootContext = parser.root();

        MySqlContext listenerSqlContext = new MySqlContext();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(new InsertSpecificationSqlListener(listenerSqlContext), rootContext);
        System.out.println( listenerSqlContext.getTableName());
        System.out.println( listenerSqlContext.getInsertForValColumnNames());
    }
}
