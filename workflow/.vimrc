"excute与normal"
set encoding=utf-8
set backspace=2 "能使用backspace回删"

set ruler "显示最后一行的状态"
set bg=dark "背景色设置  
set hlsearch "高亮度反白  
set laststatus=2 "两行状态行+一行命令行  
set cindent "设置c语言自动对齐   
set mouse-=a "设置可以在VIM使用鼠标  
set history=1000 "设置历史记录条数  
set nocompatible "设置不兼容

set tabstop=4 "设置Tab显示的宽度，Python建议设置成4
set shiftwidth=4 "这个量是每行的缩进深度，一般设置成和tabstop一样的宽度
set softtabstop=4 "表示在编辑模式的时候按退格键的时候退回缩进的长度。
set expandtab "那么设置下面这行就可以将Tab自动展开成为空格
"autocmd FileType python set expandtab "如果只想在Python文件中将Tab展开成空格，就改换成下面这句
set smartindent "智能缩进"
set autoindent "自动缩进"


""""PATHOGEN配置
""call pathogen#infect()  
""filetype plugin on "允许插件"
""syntax on "语法检测"
""filetype plugin indent on "启动智能补全"
""
"""NErdtree配置
""let g:NERDTreeDirArrows=0
""let NERDTreeDirArrowExpandable='+'
""let NERDTreeDirArrowCollapsible='~' 
""
""map <F2> :NERDTreeToggle<CR>
""
""let NERDTreeQuitOnOpen=1
""let NERDTreeShowHidden=1 "是否显示隐藏文件       
""let NERDTreeWinSize=31 "设置宽度
"""let g:nerdtree_tabs_open_on_console_startup=1 "在终端启动vim时，共享NERDTree
"""let NERDTreeIgnore=['\.pyc','\~$','\.swp'] "忽略一下文件的显示
""let NERDTreeShowBookmarks=1 "显示书签列表
""autocmd vimenter * NERDTree
""autocmd VimEnter * wincmd w
"""下面的数字要根据window的个数做判断"
""autocmd BufEnter * if (winnr("$") == 1 && exists("b:NERDTreeType") &&b:NERDTreeType == "primary")  | qa | endif
"""autocmd BufLeave * if (winnr("$") == 2 && exists("b:NERDTreeType") &&b:NERDTreeType == "primary")  | qa | endif




"插入粘贴模式
set number " 当前行显示行号
set relativenumber "其他行显示相对行号
noremap <silent> <expr> j (v:count == 0?'gj':'j') "虚行"
noremap <silent> <expr> k (v:count == 0?'gk':'k')

"缩进调整
vnoremap <tab> >gv
vnoremap <s-tab> <gv
"在插入模式时,快速跳转到行尾"
inoremap <C-e> <C-o>$
"使Y的行为与D和C一致,复制到行尾"
noremap Y y$
"使用k+j来退出insert model"
inoremap jk <ESC>
inoremap kj <ESC>
cnoremap jk <ESC>
cnoremap jk <ESC>
"插入模式使用ctrl+l来向右移动一个字符"
inoremap <C-l> <C-o>l
"插入模式自动补全" 
inoremap ( ()<Esc>i
inoremap [ []<Esc>i
inoremap { {}<Esc>i
inoremap " ""<Esc>i
inoremap < <><Esc>i
"普通模式将;映射为:"
"noremap ; :
"noremap : ;

set pastetoggle=<F3>
set clipboard=unamedplus
//离开vim不清空剪贴板
autocmd VimLeave * call system("xsel -ib", getreg('+'))
