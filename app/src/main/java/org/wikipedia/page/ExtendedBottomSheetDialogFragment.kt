package org.wikipedia.page

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class ExtendedBottomSheetDialogFragment : BottomSheetDialogFragment() {
    protected fun disableBackgroundDim() {
        requireDialog().window?.setDimAmount(0f)
    }

        class ExtendedBottomSheetDialog(context: Context, @StyleRes theme: Int) :
        BottomSheetDialog(context, theme) {

        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
            window?.let {
                BreadcrumbsContextHelper.dispatchTouchEvent(it, ev)
            }
            return super.dispatchTouchEvent(ev)
        }

        override fun onActionModeStarted(mode: ActionMode?) {
            Log.d("ExtendedBottomSheetDialogFragment", "onActionModeStarted() called with: mode = $mode")

            super.onActionModeStarted(mode)

            modifyMenu(mode!!)
        }

        override fun onActionModeFinished(mode: ActionMode?) {
            Log.d("ExtendedBottomSheetDialogFragment", "onActionModeFinished() called with: mode = $mode")
            super.onActionModeFinished(mode)
        }

        private fun modifyMenu(mode: ActionMode) {
            val menu = mode.menu
            val menuItemsList = menu.children.filter {
                val title = it.title.toString()
                !title.contains("有道翻译")
            }.toList()
            val youdao = menu.children.filter {
                val title = it.title.toString()
                title.contains("有道翻译")
            }.toList()

            menu.clear()
            youdao.forEach {
                Log.d("actionMode", "menu: + ${it.title}")
                menu.add(it.groupId, it.itemId, Menu.NONE, it.title).setIntent(it.intent).icon = it.icon
            }
            menuItemsList.forEach {
                Log.d("actionMode", "menu: + ${it.title}")
                menu.add(it.groupId, it.itemId, Menu.NONE, it.title).setIntent(it.intent).icon = it.icon
            }
        }

    }
}
