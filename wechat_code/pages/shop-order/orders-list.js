const {
list,
detail,
update,
info,
session,
} = require("../../api/index")
Page({
data: {
tabs: [],
activeTab: 0,
allList: [],
baseURL: "",
curList: "",
userInfo: "",
total:0,
page:1,
flag:false,
titles:['全部','已完成', '已发货', '已取消', '已支付', '已退款']
},

async onLoad(options) {
const tabs = this.data.titles.map(item => ({
    title: item
}))
this.setData({
    tabs
})
    this.data.titles.forEach((item, index) => {
    if (options?.name == item) {
        this.setData({
            activeTab: index
        })
    }
})
let _that=this
_that.getData(options?.name)

},
onHide() {
wx.removeStorageSync('isAuth');
},
async onShow() {
let table = wx.getStorageSync("nowTable");
const res = await session(table)
this.setData({
    userInfo: res.data
})
getApp().globalData.userInfo = res.data
},
onUnload() {
wx.switchTab({
    url: '/pages/center/center'
})
},
async  getData(name){
const parame = {
    page: this.data.page,
    limit: 10,
    sort: 'addtime',
    order: "desc",
    userid:getApp().globalData.userInfo.id
}
    if(this.data.titles.includes(name) && name!='全部'){
        parame['status']=name
    }
    const allListRes = await list('orders', parame)

let result
let mylist = this.data.allList
// if(allListRes.code==0&& allListRes.data.total>allListRes.data.list.length){
result = mylist.concat(allListRes.data.list)
// }
this.setData({
    allList: result,
    baseURL: wx.getStorageSync('baseURL') + "/",
    curList: result,
    total:allListRes.data.total,
})
},
onReachBottom() {

if (this.data.total>this.data.curList.length) {
this.setData({
page:this.data.page+1
})
this.getData(this.data.tabs[this.data.activeTab].title); // 疯狂的请求的方法
}
},
//兑换
exchange(e){
var _that=this
const item = e.currentTarget.dataset.item;
wx.showModal({
    title: '提示',
    content: '是否兑换',
    success: async function(res) {
        if (res.confirm) {
            item.status = '已完成';
            // 设置订单状态为已完成
            let res =  await update('orders', item)
            if (res.code == 0) {
                _that.getData()
                wx.showToast({
                    title: '兑换成功',
                    icon:'none'
                })
                this.onLoad()
                this.onTabCLick(this.data.activeTab)

            }
        }
    }
});
},
logisticsTap(e) {
const item = e.currentTarget.dataset.item;
wx.setStorageSync('logistics', item)
wx.navigateTo({
    url: '/pages/shop-order/logistics',
})
},
// 去评价
orderDiscuss(e) {
const item = e.currentTarget.dataset.item;
getApp().globalData.detailId=item.goodid
wx.navigateTo({
    url: `/pages/discuss${item.tablename}/update-and-add`,
})

},
// 退款
refundPriceClick(e) {
const item = e.currentTarget.dataset.item;
let table = wx.getStorageSync("nowTable");
wx.showModal({
    title: '提示',
    content: '确定要申请退款?',
    complete: async (res) => {
        if (res.confirm) {
            item.sfsh = '待审核';
            const res =await update('orders', item)
            if(res.code==0){
                wx.showToast({
                    title: '退款申请已提交',
                    icon:'none'
                })
            }
                                    
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 this.onLoad()
                     this.onTabCLick(this.data.activeTab)
                                }
    }
})
},
cancelGroup(e) {
const order = e.currentTarget.dataset.item
wx.showModal({
    title: '提示',
    content: '是否取消拼单',
    complete: async (res) => {
        if (res.confirm) {
            const res = await info(order.tablename, order.goodid)
            let goodDetail = res.data;
            goodDetail.curpeople = goodDetail.curpeople - 1;
            const table = wx.getStorageSync('nowTable')
            await update(order.tablename, goodDetail)
            order.status = '已退款';
            // 设置订单状态为取消
            await update('orders', order)
            // 重新加载数据
            if (order.type == '1', order.type == '3') {
                // 退回用户余额
                const money = Number(this.data.userInfo.money) + Number(order.total);
                // 退回用户余额
                this.setData({
                    'userInfo.money': money
                })
            } else if (order.type == '2') {
                // 退回用户积分
                const jf = Number(this.data.userInfo.jf) + Number(order.total);
                this.setData({
                    'userInfo.jf': jf
                })
            }
            const resUser = await update(table, this.data.userInfo)
            if (resUser.code == 0) {
                this.onLoad()
                this.onTabCLick(this.data.activeTab)
            }
        }
    }
})

},

async onTabCLick(e) {
let index
if(e?.detail?.index){
    index= e.detail.index
}else{
    index=e;
}
console.log('index',index);
let res;

const parame = {
    page: 1,
    limit: 10,
    sort: 'addtime',
    order: "desc",
    userid:getApp().globalData.userInfo.id
}
if (index == 0 ) {
}
if(index>0) {
    parame["status"] = this.data.tabs[index]?.title;
}
res = await list('orders', parame)
this.setData({
    activeTab: index,
    curList: res.data.list
});

},
//2版本退货

returnGood(e){
const item = e.currentTarget.dataset.item;
let table = wx.getStorageSync("nowTable");
wx.showModal({
    title: '是否退货',
    content: '退货审核通过后,金额将返回账户中?',
    complete: async (res) => {
        if (res.confirm) {
            item.status = '待审核';
            // 设置订单状态为取消
            const res=    await update('orders', item);
            if (res.code == 0) {
                // 重新加载数据
                wx.showToast({
                    title: '申请已提交',
                    icon:'none'
                })
                this.onLoad()
                this.onTabCLick(this.data.activeTab)
            }

        }
    }
})

},
//评价

// 确认收货
confrimReceipt(e) {
const item = e.currentTarget.dataset.item;
let table = wx.getStorageSync("nowTable");
wx.showModal({
    title: '提示',
    content: '是否确认收货?',
    complete: async (res) => {
        if (res.confirm) {
            item.status = '已完成';
            // 设置订单状态为取消
            let res = await update('orders', item);
            // 重新加载数据
            this.onLoad()
            this.onTabCLick(this.data.activeTab)
        }
    }
})
},
onChange(e) {
const index = e.detail.index
this.setData({
    activeTab: index
})
}

})