package per.xin.chatroom.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import per.xin.chatroom.entity.SharedFile;

@Mapper
public interface SharedFileMapper {
	/**
	 * 插入一条记录
	 * @param file
	 * @return
	 */
	public int insertSelective(SharedFile file);
	/**
	 * 通过主键查询一条数据
	 * @param fileId
	 * @return
	 */
	public SharedFile selectByPrimaryKey(Integer fileId);
	
	public List<SharedFile> selectAll();
}
