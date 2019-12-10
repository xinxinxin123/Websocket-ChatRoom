package per.xin.chatroom.mapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import per.xin.chatroom.entity.Message;
import per.xin.chatroom.entity.MessageToFore;


/**
 * message表的相关操作
 * @author liyuxin
 *
 */
@Mapper
public interface MessageMapper {
	/**
	 * 插入一条记录 ，其主键自增
	 * @param message
	 * @return
	 */
	public int insertSelective(Message message);
	
	/**
	 * 查询所有消息记录
	 * @return 消息记录集合
	 */
	public List<Message> selectAll();
	
	/**
	 * 查询所有消息记录
	 */
	public List<MessageToFore> selectRecords();
	/**
	 * 查询300消息记录
	 */
	public List<MessageToFore> selectRecords300();
	
	/**
	 * 查询私聊消息记录
	 */
	public List<MessageToFore> selectPrivateRecords(@Param("staffId") int staffId, @Param("targetId")int targetId);
	
	/**
	 * 查询私聊消息记录
	 */
	public List<MessageToFore> selectPrivateRecords(int staffId, int targetId, int number);
	
	/**
	 * 通过主键删除一条数据
	 * @return 
	 */
	
	public int deleteByPrimaryKey(Long messageId);
	/**
	 * 通过主键删除多条数据
	 */
	public int delete(List<Integer> id) ;
	/**
	 * 通过STAFF_ID删除一条数据
	 * @return 
	 */
	
	public int deleteByStaff_id(int staff_id);
	
	/**
	 * 通过消息id 找到消息 并修改消息数据
	 */
	public int updateByPrimaryKey(Message message);
	/**
	 * 取出message表中的前十条消息
	 */
	
	public List<MessageToFore> selectPublicTop10();
	
	/**
	 * 取出message表中的前n条文本消息
	 */
	
	public List<MessageToFore> selectPublicNormal(int number);
	/**
	 * 取出私聊的前十条消息
	 * @param staffId
	 * @param targetId
	 * @return List<MessageToFore>
	 */
	public List<MessageToFore> selectPrivateTop10(@Param("staffId") int staffId, @Param("targetId") int targetId);
}
