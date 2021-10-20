> 本文由 [简悦 SimpRead](http://ksria.com/simpread/) 转码， 原文地址 [zhuanlan.zhihu.com](https://zhuanlan.zhihu.com/p/128955118)

**包管理器**  
包管理器（package manager）是开发人员常用的生产力工具，Ubuntu 上的 **Apt-Get** 和 MacOS 上的 **Homebrew** 等的使用都让开发环境的搭建变得无比丝滑。这里的包，可以理解成广义上的软件，不仅包含常见的基于图形用户界面（GUI）的软件，还包含基于命令行界面（CLI）的开发工具。简单说，包管理器就是一个软件自动化管理工具。  
如今，Windows 也有了历经时间考验的包管理器：**Chocolatey**，**Scoop** 和。其中，**Chocolatey** 整个社区发布的安装脚本有 3000 多个，而 **Scoop** 官方仓库发布的安装脚本有 2000 多个。  
总的来看，上述两者各有长短。**Chocolatey** 操作简单，涵盖大量 GUI 软件，但自定义程度低，适合普通程序员甚至没有编程基础的人使用；相比之下，**Scoop** 自定义程度高，虽然默认涵盖的 GUI 软件少，但扩展性强，可以非常方便的自己定制安装脚本。适合有**喜欢折腾的**程序员使用。

本文涉及如下内容：

*   Chocolatey 的安装与基本使用；
*   Scoop 的安装；
*   Scoop 的使用（加载扩展库）；
*   Scoop 的相关配置；

**Chocolatey**  
Chocolatey 简单，先说它。

**安装**

Chocolatey 的使用，需要以管理员身份打开 Powershell（Scoop 则不需要）。

```
Set-ExecutionPolicy Bypass -Scope Process -Force;
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072;
iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
```

> 虽然 Scoop 一般不需要管理员权限，但遇到虚拟机这类必须开启管理员权限的软件时，就比较考验编程的功底了。这种情况下，有时候，Chocolatey 就会显得方便很多。

**常用命令**

*   安装、卸载与升级

```
# 安装
choco install [package_name]
## 如
choco install vscode
# 卸载
choco uninstall [package_name]
# 查看可升级版本
choco outdated
# 升级
choco upgrade [package_name]
```

![](https://pic4.zhimg.com/v2-3737b598f9c79c6a07409863e58878e7_r.jpg)

*   搜索、查看信息

```
# 搜索：查看社区中是否存在想要的软件
choco search [package_name]
# 查看信息：查看软件的简介
choco info [package_name]
```

其他命令，可使用`choco -?`查看，或前往 chotolatey 官网。  

  
**Scoop 的安装**  
**Scoop** 由澳洲程序员 Luke Sampson 于 2015 年创建，其特色之一就是其安装管理不依赖 “管理员权限”，这对使用有权限限制的公共计算机的使用者是一大利好。其的安装步骤如下：  
**步骤 1：在 PowerShell 中打开远程权限**

```
Set-ExecutionPolicy RemoteSigned -scope CurrentUser;
```

**步骤 2：自定义 Scoop 安装目录**

```
$env:SCOOP='Your_Scoop_Path'
[Environment]::SetEnvironmentVariable('SCOOP', $env:SCOOP, 'User')
```

> 如果跳过该步骤， **Scoop** 将默认把所有用户安装的 App 和 **Scoop** 本身置于`C:\Users\user_name\scoop`

**步骤 3：下载并安装 Scoop**

```
Invoke-Expression (New-Object System.Net.WebClient).DownloadString('https://get.scoop.sh')
```

**步骤 4：安装包（主要是命令行程序）：**

```
scoop install <app_name>
scoop install sudo
```

> Scoop 的基本操作与 Chotolatey 类似。

**步骤 5：通过 `scoop help` 查看使用简介**

![](https://pic1.zhimg.com/v2-bf33c33d3137ceaab7a6e1bc2f69cd54_r.jpg)

更多信息，请访问 Scoop 官网

**Scoop 的使用（加载扩展库）**

**步骤 1：安装 Aria2 来加速下载**

```
scoop install aria2
```

如果使用代理，有时需要通过如下命令关闭 aria2

```
scoop config aria2-enabled false
```

**步骤 2：安装 Git 来添加新仓库**

```
scoop install git
```

**步骤 3：添加官方维护的 extras 库（含大量 GUI 程序）**

```
scoop bucket add extras
scoop update
```

git 下载如果使用 Scoop 原生的下载协议可能比较慢，建议采用如下迂回方案：

1.  用第三方下载器，如 Motrix 下载；
2.  然后将文件拷贝到 `path to scoop/cache`；
3.  输入 scoop install git，此时会产生一个扩展名为 .download 的文件；
4.  输入 scoop uninstall git；
5.  重命名自己下载的文件名为 3 中的文件名，但取代 .download 文件；
6.  输入 scoop install git；

**可选步骤：添加我创建并维护的 scoopet 库（专注服务科研）**

```
scoop bucket add scoopet https://github.com/integzz/scoopet
scoop update
```

scoopet 库包含的安装脚本分为如下四类：  

*   科研工具：如 miniconda（国内镜像），julia（国内镜像），copytranslator，gephi，geogebra，mendeley，netlogo
*   开发辅助：如 cyberduck，virtualbox，vmware
*   日常办公：如 adobe acrobat，wpsoffice，百度网盘，灵格斯词霸
*   社交休闲：如 you-get，网易云音乐，微信

> 详情见 [https://github.com/integzz/scoopet/blob/master/README_CN.md](https://link.zhihu.com/?target=https%3A//github.com/integzz/scoopet/blob/master/README_CN.md)

![](https://pic3.zhimg.com/v2-8da86ca44dc4e071f81f05d7b73b1ffa_r.jpg)

**步骤 4：安装 App**

*   使用`scoop search` 命令搜索 App 的具体名称

```
scoop install scoop-completion
scoop install <app_name>
```

*   利用插件`scoop-completion`协助安装

```
scoop install scoop-completion
```

> 使用`scoop-completion`：键入 App 名称的前几个字母后敲击`tab`键进行补全  
> `scoop-completion`包含于 scoopet 库中

**步骤 5：查看官方推荐仓库**

```
scoop bucket known

main [默认]
extras [墙裂推荐]
versions
nightlies
nirsoft
php
nerd-fonts
nonportable
java
games
jetbrains
```

这里，推荐一个网站，这个方便全网查询安装脚本所在 bucket

  
**Scoop 的管理与配置**

**管理**

```
# 查看已安装程序
scoop list
# 查看更新
scoop status
# 删除旧版本
scoop cleanup
# 自身诊断
scoop checkup
```

![](https://pic1.zhimg.com/v2-2b41030416b39b0e1fd0a84acb3b0a68_r.jpg)

**命令行推荐**

```
# 使用 Linux 命令行
scoop install gow
# 调用管理员权限
scoop install sudo
# windows 终端模拟器
scoop install cmder
```

### **Aria2 的参数**

```
# aria2 在 Scoop 中默认开启
scoop config aria2-enabled true
# 关于以下参数的作用，详见aria2的相关资料
scoop config aria2-retry-wait 4
scoop config aria2-split 16
scoop config aria2-max-connection-per-server 16
scoop config aria2-min-split-size 4M
```