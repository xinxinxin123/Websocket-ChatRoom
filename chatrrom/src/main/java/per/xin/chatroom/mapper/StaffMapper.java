package per.xin.chatroom.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;

import per.xin.chatroom.entity.Staff;


/**
 * 表staff相关操作
 * @author liyuxin
 *
 */
@Mapper
public interface StaffMapper {
	/**
	 * 查询所有职员信息录
	 * @return 职员信息集合
	 */
	public List<Staff> selectAll();

	/**
	 * 通过主键获取一条记录
	 */
	public Staff selectByPrimaryKey(int id);

	/**
	 * 通过工号获取一条记录
	 */
	public Staff selectByCode(String code);
	/**
	 * 通过id修改职员信息
	 */
	public int updateByPrimaryKey(Staff staff);
	/**
	 * 通过主键删除一条职员信息
	 * @return
	 */

	public int deleteByPrimaryKey(int staffId);
	/**
	 * 通过工号删除一条职员信息
	 * @return
	 */

	public int deleteByCode(String code);
	/**
	 * 通过插入一条记录，主键递增
	 * @param
	 * @return
	 */
	public int insertRecord(Staff staff);

	/**
	 * 根据用户id,更改用户的头像路径
	 */
	public int updateHeadUrl(Staff staff);

	/**
	 * 根据用户id,更改用户的状态
	 */
	public int updateStateById(Staff staff);

}
