<template>
  <div class="app-container">
    <!-- 顶部功能 -->
    <div class="filter-container" style="margin-bottom: 15px">
      <!-- 类型名输入 -->
      <el-input v-model="queryParam.booktypename" placeholder="类型名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <br><br>
      <!-- 一些按钮 -->
      <el-button v-waves class="filter-item" style="font-size: 20px" type="primary" @click="handleFilter">
        搜索
      </el-button>
      <el-button v-waves class="filter-item" style="font-size: 20px" type="primary" @click="handleShowAll">
        取消
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;font-size: 20px" type="primary" @click="handleCreate">
        添加
      </el-button>
    </div>

    <!-- 弹出框 -->
    <el-dialog :title="formTitle" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="ruleForm" label-width="80px">
        <el-form-item label="类型名称" prop="booktypename">
          <el-input v-model="form.booktypename"></el-input>
        </el-form-item>
        <el-form-item label="类型描述" prop="booktypedesc">
          <el-input type="textarea" v-model="form.booktypedesc"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 数据表格 -->
    <el-table
        ref="multipleTable"
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          prop="booktypeid"
          label="序号"
          width="100">
      </el-table-column>
      <el-table-column
          prop="booktypename"
          label="类型名称"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="booktypedesc"
          label="类型描述"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          label="操作"
          width="240">
        <template slot-scope="scope">
          <el-button @click="handleUpdate(scope.row)" type="primary" style="font-size: 16px;"> 编辑</el-button>
          <el-button @click="handleDelete(scope.row,scope.$index)" type="danger" style="font-size: 16px;"> 删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页条 -->
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="queryParam.page"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="queryParam.limit"
        layout="total, sizes, prev, pager, next, jumper"
        :total="recordTotal"
        style="margin-top: 15px">
    </el-pagination>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import waves from '@/directive/waves'
import { getCount, queryBookTypesByPage, addBookType, deleteBookType, updateBookType } from '@/api/booktype'

export default {
  name: 'Bookinfo',
  directives: { waves },
  created() {
    queryBookTypesByPage(this.queryParam).then(res => {
      console.log('首页数据获取成功', res)
      this.tableData = res.data
      this.recordTotal = res.count
    })
  },
  methods: {
    handleSizeChange(curSize) {
      const params = this.queryParam
      params.limit = curSize
      queryBookTypesByPage(params).then(res => {
        console.log('分页数据获取成功', res)
        this.tableData = res.data
        this.recordTotal = res.count
      })
    },
    handleCurrentChange(curPage) {
      const params = this.queryParam
      params.page = curPage
      queryBookTypesByPage(params).then(res => {
        console.log('分页数据获取成功', res)
        this.tableData = res.data
        this.recordTotal = res.count
      })
    },
    handleFilter() {
      this.queryParam.page = 1
      queryBookTypesByPage(this.queryParam).then(res => {
        if (res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },
    handleShowAll() {
      this.queryParam.page = 1
      this.queryParam.booktypename = null
      queryBookTypesByPage(this.queryParam).then(res => {
        if (res.code === 0) {
          this.tableData = res.data
          this.recordTotal = res.count
        }
      })
    },
    handleCreate() {
      this.formType = 0
      this.form = {
        booktypeid: null,
        booktypename: '',
        booktypedesc: ''
      }
      this.dialogFormVisible = true
    },
    handleUpdate(row) {
      this.formType = 1
      this.form = {
        booktypeid: row.booktypeid,
        booktypename: row.booktypename,
        booktypedesc: row.booktypedesc
      }
      this.dialogFormVisible = true
    },
    submitForm() {
      if (this.formType === 0) {
        addBookType(this.form).then(res => {
          if (res === 1) {
            this.$message.success('添加记录成功')
            getCount().then(res => {
              this.recordTotal = res
              this.queryParam.page = Math.ceil(this.recordTotal / this.queryParam.limit)
              this.handleCurrentChange(this.queryParam.page)
            })
          } else {
            this.$message.error('添加记录失败')
          }
          this.dialogFormVisible = false
        })
      } else if (this.formType === 1) {
        updateBookType(this.form).then(res => {
          if (res === 1 || res === 0) {
            this.$message.success('更新记录成功')
            this.handleCurrentChange(this.queryParam.page)
          } else {
            this.$message.error('更新记录失败')
          }
          this.dialogFormVisible = false
        })
      }
    },
    handleDelete(row, index) {
      this.$confirm('确定要删除该条记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBookType(row).then(res => {
          if (res === 1) {
            this.$message.success('删除记录成功')
            this.tableData.splice(index, 1)
            if (this.tableData.length === 0) {
              this.queryParam.page = this.queryParam.page - 1
              this.handleCurrentChange(this.queryParam.page)
            }
          } else if (res === -1) {
            this.$message.error('该图书类型下存在图书，请先删除所属的图书再尝试删除类型')
          } else {
            this.$message.error('删除记录失败')
          }
        })
      })
    },
  },
  data() {
    return {
      tableData: [],
      recordTotal: 0,
      queryParam: {
        page: 1,
        limit: 10,
        booktypename: null
      },
      dialogFormVisible: false,
      formType: 0,
      form: {
        bookid: null,
        booktypename: '',
        booktypedesc: ''
      },
      rules: {
        booktypename: [
          { required: true, message: '请输入图书类型名称', trigger: 'blur' }
        ],
        booktypedesc: [
          { required: true, message: '请输入图书类型描述', trigger: 'blur' }
        ]
      },
    }
  },
  computed: {
    ...mapGetters(['id', 'name', 'roles']),
    formTitle() {
      return this.formType === 0 ? '添加记录' : '修改记录'
    }
  }
}
</script>

<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 150px;
  height: 200px;
  display: block;
}
</style>
