<template>
  <div class="navbar">
    <!-- <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" /> -->
<font class="breadcrumb-container"  style="font-size: 32px;">
  <b>图书管理系统</b>
</font>

    <!-- <breadcrumb class="breadcrumb-container" /> -->
    <div class="right-menu">
      <el-dropdown class="user-dropdown" trigger="click">
        <span class="user-name">{{ name }}</span>
        <i class="el-icon-caret-bottom" />
        <el-dropdown-menu slot="dropdown" class="user-dropdown-menu" style="padding-bottom: 10px;">
          <router-link to="/">
            <el-dropdown-item>
              首页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'name'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login`)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 60px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 60px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025);
    }
  }

  .breadcrumb-container {
    float: left;
    font-size: 18px;
    margin-top: 8px;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .user-dropdown {
      margin-right: 30px;
      display: flex;
      align-items: center;

      .user-name {
        font-size: 22px;
        cursor: pointer;
        margin-right: 5px;
      }

      .el-icon-caret-bottom {
        cursor: pointer;
        font-size: 12px;
      }
    }
  }
}
</style>
